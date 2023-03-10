package com.example.merlinsoftware.services

import android.content.Context
import android.util.Log
import com.example.merlinsoftware.model.PodcastDetail
import com.example.merlinsoftware.model.PodcastEpisodeDetail
import com.example.merlinsoftware.model.RootObj
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class ApiClient(context: Context) {

    private lateinit var retrofit: Retrofit

    private val cacheSize = 10 * 1024 * 1024 // 10 MB
    private val cache = Cache(context.cacheDir, cacheSize.toLong())

    private val requestIterceptor = Interceptor { chain ->
        val url = chain.request().url.newBuilder().build()

        Log.d("API_Request", "URL: $url")

        val request = chain.request().newBuilder()
            .url(url)
            .cacheControl(CacheControl.Builder().maxAge(24, TimeUnit.HOURS).build()) // Set max age to 24 hours
            .build()

        val response = chain.proceed(request)

        response.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header("Cache-Control", "public, max-age=${24 * 60 * 60}") // Set max age to 24 hours
            .build()
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(requestIterceptor)
        .cache(cache)
        .build()

    fun getClient(): Retrofit{
        retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }


    interface ApiServices {
        @GET("rss/toppodcasts/limit=100/genre=1310/json")
        fun getTopPodcasts(): Call<RootObj>

        @GET("lookup")
        fun getPodcastDetails(@Query("id") podcastId: String?): Call<PodcastDetail>

        @GET("lookup?entity=podcastEpisode&limit=9")
        fun getPodcastEpisodes(@Query("id") podcastId: String?): Call<PodcastEpisodeDetail>
    }
}