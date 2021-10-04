package com.example.infinitescroll

data class VideoResponse (
    val page: Int?,
    val limit: Int?,
    val explicit: Boolean?,
    val total: Int?,
    val has_more: Boolean?,
    val list: List<Video>?
)