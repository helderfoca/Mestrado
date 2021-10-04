package com.example.infinitescroll

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnInfinite = findViewById<Button>(R.id.infinite)
        val btnPageable = findViewById<Button>(R.id.pageable)

        btnInfinite.setOnClickListener {
            val intent = Intent(this, InfiniteScrollActivity::class.java)
            startActivity(intent)
        }

        btnPageable.setOnClickListener {
            val intent = Intent(this, PageableActivity::class.java)
            startActivity(intent)
        }

    }

}