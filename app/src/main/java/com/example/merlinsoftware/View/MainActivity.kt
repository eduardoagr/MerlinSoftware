package com.example.merlinsoftware.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.merlinsoftware.adapter.PodcastAdapter
import com.example.merlinsoftware.services.ApiClient
import com.example.merlinsoftware.databinding.ActivityMainBinding
import com.example.merlinsoftware.model.RootObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val podcastAdapter by lazy { PodcastAdapter(this) }
    private val api: ApiClient.ApiServices by lazy {
        ApiClient(this).getClient().create(ApiClient.ApiServices::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // show loading
        binding.mainProgressBar.visibility = View.VISIBLE

        // set up recycler view
        binding.mainRecycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = podcastAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        // get data from api
        api.getTopPodcasts().enqueue(object : Callback<RootObj> {
            override fun onResponse(call: Call<RootObj>, response: Response<RootObj>) {
                binding.mainProgressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    response.body()?.let { itData ->
                        podcastAdapter.setData(itData.feed.entry)
                    }
                } else {
                    Log.e("Edu", "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RootObj>, t: Throwable) {
                binding.mainProgressBar.visibility = View.GONE
                Log.e("Edu", "onFailure: ${t.message}")
            }
        })

        // set up search view
        binding.mainSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                podcastAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                podcastAdapter.filter.filter(newText)
                return false
            }
        })
    }
}


