package com.pankaj.dojoin.ui.video_recording

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.otaliastudios.cameraview.controls.Audio
import com.otaliastudios.cameraview.controls.Flash
import com.pankaj.dojoin.databinding.ActivityVideoRecordingBinding
import com.pankaj.dojoin.utils.Constants
import com.pankaj.dojoin.utils.Utils
import com.pankaj.dojoin.utils.segment_progress.ProgressBarListener
import java.io.File
import java.io.IOException

class VideoRecordingViewModel : ViewModel() {

    var audioClickable = MutableLiveData<Boolean>()
    var doneClickable = MutableLiveData<Boolean>()
    var cameraClickable = MutableLiveData<Boolean>()
    var isRecording = MutableLiveData<Boolean>()
    var isFlashOn = MutableLiveData<Boolean>()
    var is_recording_timer_enable = MutableLiveData<Boolean>()
    var sec_passed = 0
    var recording_time = 3
    var number = 0
    var videopaths = ArrayList<String>()
    lateinit var mActivity: Activity
    lateinit var binding: ActivityVideoRecordingBinding

    init {
        cameraClickable.value = true
        doneClickable.value = false
        audioClickable.value = true
        isRecording.value = false
        isFlashOn.value = false
        is_recording_timer_enable.value = false
    }


    fun initVar(mActivity: Activity, binding: ActivityVideoRecordingBinding) {

        this.mActivity = mActivity
        this.binding = binding
    }


    fun initlize_Video_progress() {
        sec_passed = 0
        binding.videoProgress.enableAutoProgressView(Constants.recording_duration)
        binding.videoProgress.setDividerColor(Color.WHITE)
        binding.videoProgress.setDividerEnabled(true)
        binding.videoProgress.setDividerWidth(4F)
        binding.videoProgress.setShader(
            intArrayOf(
                Color.CYAN,
                Color.CYAN,
                Color.CYAN
            )
        )
        binding.videoProgress.SetListener(object : ProgressBarListener {
            override fun TimeinMill(mills: Long) {
                sec_passed = (mills / 1000).toInt()
                if (sec_passed > Constants.recording_duration / 1000 - 1) {
                    StartStopRecording()
                }
                if (is_recording_timer_enable.value!! && sec_passed >= recording_time) {
                    is_recording_timer_enable.value = false
                    StartStopRecording()
                }
            }
        })
    }


    // if the Recording is stop then it we start the recording
    // and if the mobile is recording the video then it will stop the recording
    fun StartStopRecording() {
        if (Constants.Selected_sound_id.isNotEmpty() && Constants.Selected_sound_id == "null")
            binding.camera!!.audio = Audio.ON
        else
            binding.camera!!.audio = Audio.OFF

        if (!isRecording.value!! && sec_passed < Constants.recording_duration / 1000 - 1) {
            number = number + 1
            isRecording.value = true
            audioClickable.value = false
            doneClickable.value = false
            cameraClickable.value = false

            val file = File(Constants.app_folder + "myvideo" + number + ".mp4")
            videopaths.add(Constants.app_folder + "myvideo" + number + ".mp4")
            //  binding.camera!!.(file)
            binding.camera.takeVideo(file);

            binding.videoProgress.resume()
        } else if (isRecording.value!!) {
            isRecording.value = false
            binding.videoProgress.pause()
            binding.videoProgress.addDivider()
            binding.camera!!.stopVideo()

            Log.d("time sec_passed", sec_passed.toString())
            Log.d("time min", ((Constants.recording_duration / 1000) / 3).toString())
            if (sec_passed > Constants.recording_duration / 1000 / 3) {
                doneClickable.value = true
            }
            cameraClickable.value = true
        } else if (sec_passed > Constants.recording_duration / 1000) {
            Utils.Show_Alert(
                mActivity,
                "Alert",
                "Video only can be a " + Constants.recording_duration as Int / 1000 + " S"
            )
        }
    }


    /**
     * OnClick
     */

    fun onRecordClick(v: View) {
        StartStopRecording()
    }

    fun RotateCamera(v: View) {
        binding.camera!!.toggleFacing()
    }

    fun onFlashClick(v: View) {
        if (isFlashOn.value!!) {
            isFlashOn.value = false
            binding.camera!!.flash = Flash.OFF
        } else {
            isFlashOn.value = true
            binding.camera!!.flash = Flash.ON
        }
    }

    fun onBackPress(v: View) {

        mActivity.onBackPressed()
    }


    fun onDoneClick(view: View) {
        (mActivity as VideoRecordingActivity).append()

    }


}