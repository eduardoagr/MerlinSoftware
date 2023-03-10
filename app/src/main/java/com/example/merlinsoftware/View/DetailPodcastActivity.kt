package com.example.merlinsoftware.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.merlinsoftware.R
import com.example.merlinsoftware.adapter.EpisodeAdapter
import com.example.merlinsoftware.adapter.PodcastAdapter
import com.example.merlinsoftware.databinding.ActivityDetailPodcastBinding
import com.example.merlinsoftware.model.EpisodeInfo
import com.example.merlinsoftware.model.PodcastDetail
import com.example.merlinsoftware.model.PodcastEpisodeDetail
import com.example.merlinsoftware.services.ApiClient
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPodcastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPodcastBinding
    private val episodeAdapter by lazy { EpisodeAdapter(this) }
    private val api: ApiClient.ApiServices by lazy {
        ApiClient(this).getClient().create(ApiClient.ApiServices::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityDetailPodcastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressBar = binding.header.progressIndicator
        val appTitle = binding.header.appTitle
        appTitle.setOnClickListener {
            // Display progress bar
            progressBar.visibility = View.VISIBLE

            // Start navigation process
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Hide progress bar after transition is complete
            progressBar.visibility = View.GONE
        }

        val podcastID = intent.getStringExtra("id")
        val podcastTitle = intent.getStringExtra("title")
        val podcastArtist = intent.getStringExtra("Author")
        val podcastDesc = intent.getStringExtra("desc")

        binding.apply {
            GlobalScope.launch(Dispatchers.IO) {
                val podcastDetail = async { api.getPodcastDetails(podcastID).execute().body() }
                val podcastEpisodes = async { api.getPodcastEpisodes(podcastID).execute().body() }

                withContext(Dispatchers.Main) {
                    podcastDetail.await()?.let { data ->
                        podcastDetailTitle.text = buildString {
                            append("Title: ")
                            append(podcastTitle)
                        }
                        podcastDetailAuthor.text = buildString {
                            append("Author: ")
                            append(podcastArtist)
                        }
                        podcastDetailDescription.text = buildString {
                            append("Description: ")
                            append(podcastDesc)
                        }

                        podcastDetailEpisodes.text = buildString {
                            append("Episodes: ")
                            append(data.results[0].trackCount.toString())
                        }

                        Glide.with(applicationContext)
                            .load(data.results[0].artworkUrl100)
                            .override(
                                100,
                                100
                            ) // Specify the desired width and height in pixels
                            .into(podcastDetailImage)

                    }
                    podcastEpisodes.await()?.let { data ->

                        episodeAdapter.setData(data.results)
                        episodesRecyclerDetails.apply {
                            adapter = episodeAdapter
                            layoutManager = LinearLayoutManager(context)
                            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                        }
                    }
                }
            }
        }
    }
}