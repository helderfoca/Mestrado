package com.example.infinitescroll

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {

    const val NETWORK_TIMEOUT = 10L
    /**
     *  This method is used to create the Retrofit builder to allow to make the network requests
     *  'baseUrl' - Base URL used by the API
     *  'T' - Pass the API correspondent interface, i.e. -> NetworkManager.create<NewsAPI>(baseUrl = "https://newsapi.org/v2/")
     **/
    inline fun <reified T> create(baseUrl: String) =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder().apply {
                connectTimeout(NETWORK_TIMEOUT, TimeUnit.MINUTES)
                readTimeout(NETWORK_TIMEOUT, TimeUnit.MINUTES)
                writeTimeout(NETWORK_TIMEOUT, TimeUnit.MINUTES)
            }.build())
            .baseUrl(baseUrl)
            .build().create(T::class.java)

}