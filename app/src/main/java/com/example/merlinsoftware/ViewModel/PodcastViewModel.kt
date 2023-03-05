package com.example.merlinsoftware.ViewModel

import Podcast
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.merlinsoftware.Repository.PodcastRepository
import kotlinx.coroutines.launch

class PodcastViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PodcastRepository(application)

    private val _podcasts = MutableLiveData<List<Podcast>>()
    val podcasts: LiveData<List<Podcast>> = _podcasts

    fun loadPodcasts() {
        viewModelScope.launch {
            val result = repository.getTopPodcasts()
            _podcasts.value = result
        }
    }

    fun searchPodcasts(query: String) {
        viewModelScope.launch {
            val result = repository.searchPodcasts(query)
            _podcasts.value = result
        }
    }
}