package com.example.infinitescroll

import retrofit2.http.GET
import retrofit2.http.Query

class VideoRepository {

    private val manager = NetworkManager.create<VideoRepositoryAPI>(
        baseUrl = "https://api.dailymotion.com/"
    )

    interface VideoRepositoryAPI {

        @GET("videos")
        suspend fun getVideos(
            @Query("channel") channel: String?,
            @Query("page") page: Int?,
            @Query("limit") limit: Int?
        ): VideoResponse

    }

    suspend fun getVideos(
        page: Int?, limit: Int?
    ): VideoResponse? = runCatching { manager.getVideos(channel="news",page, limit) }.getOrNull()

}