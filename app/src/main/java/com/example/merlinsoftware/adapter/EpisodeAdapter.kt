package com.example.merlinsoftware.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.merlinsoftware.R
import com.example.merlinsoftware.databinding.EpisodeItemBinding
import com.example.merlinsoftware.model.EpisodeInfo
import com.example.merlinsoftware.view.DetailEpisodeActivity

class EpisodeAdapter(private val context: Context) : RecyclerView.Adapter<EpisodeAdapter.ViewHolder>() {

    private var episodeList = mutableListOf<EpisodeInfo>()


    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<EpisodeInfo>) {
        episodeList.clear()
        episodeList.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = EpisodeItemBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(context: Context, episode: EpisodeInfo) {
            binding.apply {
                episodeTitle.text = "Title: " + episode.trackName
                // Convert the duration to hours, minutes, and seconds
                val durationInSeconds = episode.trackTimeMillis / 1000
                val hours = durationInSeconds / 3600
                val minutes = (durationInSeconds % 3600) / 60
                val seconds = durationInSeconds % 60

                episodeDuration.text = "Duration: " + String.format("%02d:%02d:%02d", hours, minutes, seconds)
                episodeReleaseDate.text = "Release on: " + episode.releaseDate

                root.setOnClickListener {
                    val intent = Intent(context, DetailEpisodeActivity::class.java)
                    intent.putExtra("title", episode.trackName)
                    intent.putExtra("artist", episode.artistName)
                    intent.putExtra("desc", episode.shortDescription)
                    intent.putExtra("url",  episode.artistViewUrl)
                    intent.putExtra("img",  episode.artworkUrl600)
                    intent.putExtra("feed", episode.feedUrl)
                    context.startActivity(intent)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.episode_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, episodeList[position])
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }
}