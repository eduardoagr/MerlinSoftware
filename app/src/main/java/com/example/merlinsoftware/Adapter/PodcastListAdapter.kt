package com.example.merlinsoftware.Adapter

import Podcast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.merlinsoftware.R

class PodcastListAdapter(private val podcastList: List<Podcast>) : RecyclerView.Adapter<PodcastListAdapter.PodcastViewHolder>() {

    private var onPodcastClickListener: ((Podcast) -> Unit)? = null

    fun setOnPodcastClickListener(listener: (Podcast) -> Unit) {
        onPodcastClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_podcast, parent, false)
        return PodcastViewHolder(view)
    }

    override fun getItemCount(): Int {
        return podcastList.size
    }

    override fun onBindViewHolder(holder: PodcastViewHolder, position: Int) {
        holder.bind(podcastList[position])
        onPodcastClickListener?.let { listener ->
            holder.itemView.setOnClickListener {
                listener(podcastList[position])
            }
        }
    }

    class PodcastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val artistTextView: TextView = itemView.findViewById(R.id.artistTextView)
        private val releaseDateTextView: TextView = itemView.findViewById(R.id.releaseDateTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(podcast: Podcast) {
            titleTextView.text = podcast.title
            artistTextView.text = podcast.author
            releaseDateTextView.text = podcast.releaseDate.toString()

            Glide.with(imageView.context)
                .load(podcast.imageUrl)
                .into(imageView)
        }
    }
}