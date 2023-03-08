package com.example.merlinsoftware.view

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.merlinsoftware.adapter.PodcastAdapter
import com.example.merlinsoftware.services.ApiClient
import com.example.merlinsoftware.services.ApiServices
import com.example.merlinsoftware.databinding.ActivityMainBinding
import com.example.merlinsoftware.model.Feed
import com.example.merlinsoftware.model.RootObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val podcastAdapter by lazy { PodcastAdapter() }
    private val api: ApiServices by lazy {
        ApiClient(this).getClient().create(ApiServices::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //InitViews
        binding.apply {
            //show loading
            mainProgressBar.visibility = View.VISIBLE
            //Call movies api
            val callMoviesApi = api.getTopPodcasts()
            callMoviesApi.enqueue(object : Callback<Feed> {
                override fun onResponse(call: Call<Feed>, response: Response<Feed>) {
                    mainProgressBar.visibility = View.GONE
                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.entry.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        val TAG = "Edu"
                                        Log.e(TAG, "onResponse: " + itData.size,)
                                    }
                                }
                            }
                        }
                        in 300..399 -> {
                            Log.d("Response Code", " Redirection messages : ${response.code()}")
                        }
                        in 400..499 -> {
                            Log.d("Response Code", " Client error responses : ${response.code()}")
                        }
                        in 500..599 -> {
                            Log.d("Response Code", " Server error responses : ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<Feed>, t: Throwable) {
                    mainProgressBar.visibility = View.GONE
                }

            })
        }
    }
}
