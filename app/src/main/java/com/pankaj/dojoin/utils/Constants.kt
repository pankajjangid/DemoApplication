package com.pankaj.dojoin.utils

import android.os.Environment

class Constants {

    companion object {
        var BASE_URL = "http://indiakatelant.com/"
        var FIREBASE_DEEP_LINK = "https://indiatalent.page.link"

        val APP_FOLDER = "India Ka Talent"
        const val tag = "Constants"
        const val shareText = "Check my profile : "
        const val shareVideoText = "Check this awesome video : "

        val root = Environment.getExternalStorageDirectory().toString()
        val app_folder = "$root/India Ka Talent/"
        val draft_app_folder = app_folder + "Draft/"

        var Selected_sound_id = "null"


        var gallery_trimed_video: String =
            app_folder + "gallery_trimed_video.mp4"
        var gallery_resize_video: String =
            app_folder + "gallery_resize_video.mp4"

        var outputfile = "$app_folder output.mp4"
        var outputfile2: String = "$app_folder output2.mp4"
        val output_filter_file: String = app_folder + "output-filtered.mp4"

        const val SelectedAudio_MP3 = "SelectedAudio.mp3"
        const val SelectedAudio_AAC = "SelectedAudio.aac"
        const val Pick_video_from_gallery = 791


        var max_recording_duration = 18000L
        var recording_duration = 18000L
        val ENABLE_MOBILE_LOGIN = true


        const val DATE_TIME_FORMAT = "dd MMMM yyyy , hh:mm a"
        const val DATE_FORMAT = "MM/dd/yyyy"
        const val DATE_FORMAT_IN_API = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    }

}