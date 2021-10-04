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


class InfiniteScrollActivity : AppCompatActivity() {

    private var videos = mutableListOf<Video?>()
    private lateinit var videoViewModel: VideosViewModel

    // pagination
    val limit = 10
    var first = true
    var currentPage = 1
    var totalPages = 100
    var loading = true
    var visibleItemCount = 0
    var totalItemCount = 0
    var pastVisibleItems = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infinite_scroll)

        val rvVideos = findViewById<RecyclerView>(R.id.videos)

        videoViewModel = ViewModelProvider(viewModelStore, defaultViewModelProviderFactory).get(
            VideosViewModel::class.java
        )

        videoViewModel.getVideos(currentPage, limit) { response ->
            response?.list?.let { newVideos ->
                response.total?.let { vids ->
                    totalPages = vids / limit
                }
                videos.addAll(newVideos)
                runOnUiThread {
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
                        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                if ((dy > 0 || first) && currentPage + 1 < totalPages) { //check for scroll down
                                    first = false
                                    recyclerView.layoutManager?.let { layoutManager ->
                                        visibleItemCount = layoutManager.childCount
                                        totalItemCount = layoutManager.itemCount
                                        pastVisibleItems =
                                            (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                                        if (loading) {
                                            // verifica se deu scroll até ao fim
                                            if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                                                loading = false
                                                currentPage++
                                                videos.add(null)
                                                runOnUiThread {
                                                    rvVideos.adapter?.notifyItemInserted(videos.size - 1)
                                                }
                                                videoViewModel.getVideos(
                                                    currentPage,
                                                    limit
                                                ) { response ->
                                                    response?.list?.let { stations ->
                                                        videos.removeAt(videos.size - 1)
                                                        videos.addAll(stations)
                                                        runOnUiThread {
                                                            rvVideos?.adapter?.notifyDataSetChanged()
                                                        }
                                                        loading = true
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        })
                    }
                }
            }
        }

    }

}