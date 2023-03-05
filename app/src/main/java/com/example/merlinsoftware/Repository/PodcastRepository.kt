package com.example.merlinsoftware.Repository

import PodcastEntryJson
import PodcastListJson
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class PodcastRepository {

    companion object {
        private const val TOP_COMEDY_PODCASTS_URL =
            "https://itunes.apple.com/us/rss/toppodcasts/limit=100/genre=1310/json"
    }

    private val client = OkHttpClient()
    private val moshi = Moshi.Builder().build()
    private val jsonAdapter = moshi.adapter(PodcastListJson::class.java)

    @Throws(IOException::class)
    suspend fun getTopPodcasts(): List<PodcastEntryJson> {
        val request = Request.Builder()
            .url(TOP_COMEDY_PODCASTS_URL)
            .build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            throw IOException("Unexpected error code: ${response.code()}")
        }

        val body = response.body()?.string()
        val res = jsonAdapter.fromJson(body)
        return res?.feed?.entry ?: emptyList()
    }
}