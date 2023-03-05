package com.example.merlinsoftware.Service

import Podcast
import PodcastListJson
import com.example.merlinsoftware.Interfaces.PodcastDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class PodcastService(private val podcastDao: PodcastDao) {
    private val API_URL = "https://itunes.apple.com/us/rss/toppodcasts/limit=100/genre=1310/json"
    private val CACHE_DURATION = 24 * 60 * 60 * 1000 // 1 day in milliseconds
    private val moshi = Moshi.Builder().build()

    suspend fun getPodcasts(): List<Podcast> {
        val cachedPodcasts = podcastDao.getAll()
        if (cachedPodcasts.isNotEmpty() && !isCacheExpired(cachedPodcasts.first().fetchTime)) {
            return cachedPodcasts
        } else {
            val response = withContext(Dispatchers.IO) { fetchPodcastsFromApi() }
            if (response.isSuccessful) {
                val podcastListJson = response.body()?.string()
                val podcasts = parsePodcastsJson(podcastListJson)
                podcasts.forEach { it.fetchTime = System.currentTimeMillis() }
                podcastDao.insertAll(podcasts)
                return podcasts
            } else {
                throw RuntimeException("Failed to fetch podcasts from API")
            }
        }
    }


    private suspend fun fetchPodcastsFromApi(): okhttp3.Response {
        val request = okhttp3.Request.Builder()
            .url(API_URL)
            .build()
        val client = okhttp3.OkHttpClient.Builder().build()
        return client.newCall(request).execute()
    }

    private fun parsePodcastsJson(json: String?): List<Podcast> {
        val podcasts = mutableListOf<Podcast>()
        val podcastListAdapter = moshi.adapter(PodcastListJson::class.java)
        val podcastListJson = podcastListAdapter.fromJson(json)
        podcastListJson?.feed?.entry?.forEach { entry ->
            val dateString = entry.releaseDate?.label
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
            val releaseDate = dateFormat.parse(dateString)

            val podcast = Podcast(
                entry.id,
                entry.title.label,
                entry.artist.label,
                entry.summary?.label ?: "",
                entry.image?.url ?: "",
                releaseDate ?: Date(),
                entry.content?.get(0)?.url ?: ""
            )
            podcasts.add(podcast)
        }
        return podcasts
    }

    private fun isCacheExpired(fetchTime: Long): Boolean {
        return System.currentTimeMillis() - fetchTime > CACHE_DURATION
    }
}