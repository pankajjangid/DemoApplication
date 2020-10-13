package com.pankaj.dojoin.ui.video_recording

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pankaj.dojoin.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class VideoRecordingFactory(
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VideoRecordingViewModel() as T
    }
}