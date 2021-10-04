package com.example.infinitescroll

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PageableActivity : AppCompatActivity() {

    private var videos = mutableListOf<Video?>()
    private lateinit var videoViewModel: VideosViewModel

    // pagination
    val limit = 10
    var currentPage = 1
    var totalPages = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pageable)

        videoViewModel = ViewModelProvider(viewModelStore, defaultViewModelProviderFactory).get(
            VideosViewModel::class.java
        )

        val btnBack = findViewById<ImageView>(R.id.back)
        val btnNext = findViewById<ImageView>(R.id.next)

        updateVideos()

        btnBack.setOnClickListener {
            if (currentPage > 1) {
                currentPage--
                updateVideos()
            }
        }

        btnNext.setOnClickListener {
            if (currentPage < totalPages) {
                currentPage++
                updateVideos()
            }
        }

    }

    fun updateVideos() {

        val rvVideos = findViewById<RecyclerView>(R.id.videos)
        val pageDisplay = findViewById<TextView>(R.id.pagination)

        videoViewModel.getVideos(currentPage, limit) { response ->
            response?.list?.let { newVideos ->
                response.total?.let { vids ->
                    totalPages = vids / limit
                }
                videos.clear()
                videos.addAll(newVideos)
                runOnUiThread {
                    pageDisplay.text = "$currentPage / $totalPages"
                    rvVideos?.apply {
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        adapter = ListAdapter(
                            R.layout.video,
                            videos,
                            { itemView, item ->
                                item?.let { vid ->
                                    itemView.findViewById<TextView>(R.id.category)?.text =
                                        vid.channel
                                    itemView.findViewById<TextView>(R.id.title)?.text = vid.title
                                    itemView.findViewById<ImageView>(R.id.play).setOnClickListener {
                                        val intent = Intent()
                                        intent.action = Intent.ACTION_VIEW
                                        intent.addCategory(Intent.CATEGORY_BROWSABLE)
                                        intent.data =
                                            Uri.parse(getString(R.string.video_link, vid.id))
                                        startActivity(intent)
                                    }
                                }
                            },
                            { _, _ ->
                            }
                        )
                    }
                }
            }
        }
    }
}