package com.example.merlinsoftware.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.merlinsoftware.adapter.PodcastAdapter
import com.example.merlinsoftware.model.Podcast
import com.example.merlinsoftware.services.ApiClient
import com.example.merlinsoftware.services.ApiServices
import com.example.merlinsoftware.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val podcastAdapter by lazy { PodcastAdapter() }
    private val api : ApiServices by lazy {
        ApiClient(this).getClient().create(ApiServices::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            mainProgressBar.visibility = View.VISIBLE

            val callPodcastApi = api.getTopPodcasts
            callPodcastApi.enqueue(object : retrofit2.Callback<Podcast>{
                override fun onResponse(call: Call<Podcast>, response: Response<Podcast>) {

                    mainProgressBar.visibility = View.GONE
                    when(response.code()){
                        // successful response
                        in 200..299->{
                            response.body().let { b ->
                                b?.feed.let {data ->
                                    if (data!!.isNotEmpty()){

                                        podcastAdapter.differ.submitList(data)
                                        mainRecycler.apply {
                                            layoutManager = LinearLayoutManager(this@MainActivity)
                                            adapter = podcastAdapter
                                        }

                                    }
                                }
                            }
                        }
                        // redirection response
                        in 300..399->{}
                        // client error response
                        in 400..499 ->{}
                        // server error response
                        in 500..599->{}
                    }
                }

                override fun onFailure(call: Call<Podcast>, t: Throwable) {
                    mainProgressBar.visibility = View.GONE
                    Log.e("onFailure", "Err : ${t.message}")
                }

            })
        }

    }
}