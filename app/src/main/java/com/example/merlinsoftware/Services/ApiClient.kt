package com.example.merlinsoftware.Services

import android.content.Context
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(context: Context) {

    private lateinit var retrofit: Retrofit


    private val cacheSize = 10 * 1024 * 1024 // 10 MB
    private val cache = Cache(context.cacheDir, cacheSize.toLong())

    private val requestIterceptor = Interceptor { chain ->
        val url = chain.request().url.newBuilder().build()

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

}