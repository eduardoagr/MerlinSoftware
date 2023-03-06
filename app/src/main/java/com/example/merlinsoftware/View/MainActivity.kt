package com.example.merlinsoftware.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.merlinsoftware.Adapter.PodcastAdapter
import com.example.merlinsoftware.R

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PodcastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.main_recycler)
        recyclerView.adapter = adapter
    }
}