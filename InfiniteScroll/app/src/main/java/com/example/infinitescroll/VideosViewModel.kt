package com.example.infinitescroll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideosViewModel : ViewModel() {

    private val videoRepository by lazy { VideoRepository()  }

    fun getVideos(page: Int, limit: Int, onFinish: (VideoResponse?) -> Unit) = viewModelScope.launch(Dispatchers.IO)
    {
        val response = videoRepository.getVideos(page, limit)
        onFinish(response)
    }
}