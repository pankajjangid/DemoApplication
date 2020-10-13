package com.pankaj.dojoin.ui.video_recording

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.*
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.androidisland.ezpermission.EzPermission
import com.otaliastudios.cameraview.CameraException
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraOptions
import com.otaliastudios.cameraview.VideoResult
import com.otaliastudios.cameraview.frame.Frame
import com.otaliastudios.cameraview.frame.FrameProcessor
import com.pankaj.dojoin.R
import com.pankaj.dojoin.databinding.ActivityVideoRecordingBinding
import com.pankaj.dojoin.utils.Constants
import kotlinx.android.synthetic.main.activity_selection.*
import kotlinx.android.synthetic.main.activity_video_recording.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.ByteArrayOutputStream
import java.io.File

class VideoRecordingActivity : AppCompatActivity(), KodeinAware {

    companion object {
        private const val USE_FRAME_PROCESSOR = true
        private const val DECODE_BITMAP = false

    }

    lateinit var mActivity: Activity
    lateinit var mBinding: ActivityVideoRecordingBinding
    lateinit var mViewModel: VideoRecordingViewModel
    val factory: VideoRecordingFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()



        initViews()

        EzPermission.with(mActivity)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .request { granted, denied, permanentlyDenied ->
                //Here you can check results...
                if (granted.contains("Manifest.permission.CAMERA") && granted.contains("Manifest.permission.WRITE_EXTERNAL_STORAGE")){

                }
            }

    }

    /**
     * initialize the data binding
     */
    private fun initBinding() {
        mActivity = this
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_recording)
        mViewModel = ViewModelProvider(this, factory).get(VideoRecordingViewModel::class.java)
        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this

        setSupportActionBar(toolbar)

        supportActionBar?.apply {


        }


        mViewModel.initVar(mActivity, mBinding)

    }


    // this will delete all the video parts that is create during priviously created video
    var delete_count = 0
    fun DeleteFile() {
        delete_count++
        val output = File(Constants.outputfile)
        val output2 = File(Constants.outputfile2)
        val output_filter_file = File(Constants.output_filter_file)
        if (output.exists()) {
            output.delete()
        }
        if (output2.exists()) {
            output2.delete()
        }
        if (output_filter_file.exists()) {
            output_filter_file.delete()
        }
        val file =
            File(Constants.app_folder.toString() + "myvideo" + delete_count + ".mp4")
        if (file.exists()) {
            file.delete()
            DeleteFile()
        }
    }

    @SuppressLint("NewApi")
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }


    // this will apped all the videos parts in one  fullvideo
    fun append(): Boolean {
/*

        val progressDialog =
            ProgressDialog(this@VideoRecordingActivity)
        Thread(Runnable {
            runOnUiThread {
                progressDialog.setMessage("Please wait..")
                progressDialog.show()
            }
            val video_list =
                ArrayList<String>()
            for (i in mViewModel.videopaths.indices) {
                val file = File(mViewModel.videopaths[i])
                if (file.exists()) {
                    try {
                        val retriever = MediaMetadataRetriever()
                        retriever.setDataSource(
                            this@VideoRecordingActivity,
                            Uri.fromFile(file)
                        )
                        val hasVideo =
                            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO)
                        val isVideo = "yes" == hasVideo
                        if (isVideo && file.length() > 3000) {
                            Log.d("resp", mViewModel.videopaths[i])
                            video_list.add(mViewModel.videopaths[i])
                        }
                    } catch (e: java.lang.Exception) {
                        Log.d(Constants.tag, e.toString())
                    }
                }
            }
            try {
                val inMovies =
                    arrayOfNulls<Movie>(video_list.size)
                for (i in video_list.indices) {
                    inMovies[i] = MovieCreator.build(video_list[i])
                }
                val videoTracks: MutableList<Track> =
                    LinkedList()
                val audioTracks: MutableList<Track> =
                    LinkedList()
                for (m in inMovies) {
                    for (t in m!!.tracks) {
                        if (t.handler == "soun") {
                            audioTracks.add(t)
                        }
                        if (t.handler == "vide") {
                            videoTracks.add(t)
                        }
                    }
                }
                val result =
                    Movie()
                if (audioTracks.size > 0) {
                    result.addTrack(AppendTrack(*audioTracks.toTypedArray()))
                }
                if (videoTracks.size > 0) {
                    result.addTrack(AppendTrack(*videoTracks.toTypedArray()))
                }
                val out = DefaultMp4Builder().build(result)
                var outputFilePath: String? = null
                outputFilePath = if (viewModel.audio != null) {
                    Constants.outputfile
                } else {
                    Constants.outputfile2
                }
                val fos =
                    FileOutputStream(File(outputFilePath))
                out.writeContainer(fos.channel)
                fos.close()
                runOnUiThread {
                    progressDialog.dismiss()
                    if (viewModel.audio != null) Merge_withAudio() else {
                        Go_To_preview_Activity()
                    }
                }
            } catch (e: java.lang.Exception) {
            }
        }).start()

*/


        return true
    }


    private fun initViews() {


        val intent = intent
        Constants.Selected_sound_id = "null"

        Constants.recording_duration = Constants.max_recording_duration
        camera!!.setLifecycleOwner(this)
        camera!!.addCameraListener(Listener())
        camera!!.addCameraListener(Listener())
        if (USE_FRAME_PROCESSOR) {
            camera!!.addFrameProcessor(object : FrameProcessor {
                private var lastTime = System.currentTimeMillis()
                override fun process(frame: Frame) {
                    val newTime = frame.time
                    val delay = newTime - lastTime
                    lastTime = newTime
                    /* LOG.v(
                         "Frame delayMillis:",
                         delay,
                         "FPS:",
                         1000 / delay
                     )*/
                    if (DECODE_BITMAP) {
                        if (frame.format == ImageFormat.NV21
                            && frame.dataClass == ByteArray::class.java
                        ) {
                            val data = frame.getData<ByteArray>()
                            val yuvImage = YuvImage(
                                data,
                                frame.format,
                                frame.size.width,
                                frame.size.height,
                                null
                            )
                            val jpegStream =
                                ByteArrayOutputStream()
                            yuvImage.compressToJpeg(
                                Rect(
                                    0, 0,
                                    frame.size.width,
                                    frame.size.height
                                ), 100, jpegStream
                            )
                            val jpegByteArray = jpegStream.toByteArray()
                            val bitmap = BitmapFactory.decodeByteArray(
                                jpegByteArray,
                                0, jpegByteArray.size
                            )
                            bitmap.toString()
                        }
                    }
                }
            })
        }


        mViewModel.initlize_Video_progress()

    }


    fun getfileduration(uri: Uri?): Long {
        try {
            val mmr = MediaMetadataRetriever()
            mmr.setDataSource(this, uri)
            val durationStr =
                mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            val file_duration = durationStr?.toInt()
            if (file_duration != null) {
                return file_duration.toLong()
            }
        } catch (e: Exception) {
        }
        return 0
    }


    private inner class Listener : CameraListener() {
        override fun onCameraOpened(options: CameraOptions) {

        }

        override fun onCameraError(exception: CameraException) {
            super.onCameraError(exception)

        }

        override fun onVideoTaken(result: VideoResult) {
            super.onVideoTaken(result)
            //  LOG.w("onVideoTaken called! Launching activity.")

        }

        override fun onVideoRecordingStart() {
            super.onVideoRecordingStart()
        }

        override fun onVideoRecordingEnd() {
            super.onVideoRecordingEnd()

        }

        override fun onExposureCorrectionChanged(
            newValue: Float,
            bounds: FloatArray,
            fingers: Array<PointF>?
        ) {
            super.onExposureCorrectionChanged(newValue, bounds, fingers)
        }

        override fun onZoomChanged(newValue: Float, bounds: FloatArray, fingers: Array<PointF>?) {
            super.onZoomChanged(newValue, bounds, fingers)
        }
    }

    override val kodein: Kodein by kodein()

}