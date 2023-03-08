package com.example.merlinsoftware.Services

import com.example.merlinsoftware.Model.Podcast
import com.example.merlinsoftware.Model.PodcastDetail
import com.example.merlinsoftware.Model.PodcastEpisodeDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServices {
    @get:GET("rss/toppodcasts/limit=100/genre=1310/json")
    val getTopPodcasts: Call<Podcast>

    @GET("lookup")
    fun getPodcastDetails(@Query("id") podcastId: String?): Call<PodcastDetail>

    @GET("lookup")
    fun getPodcastEpisodes(
        @Query("id") podcastId: String?,
    ): Call<PodcastEpisodeDetail>
}