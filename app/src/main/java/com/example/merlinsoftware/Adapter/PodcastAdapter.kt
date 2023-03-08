package com.example.merlinsoftware.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.merlinsoftware.Model.Podcast
import com.example.merlinsoftware.R
import com.example.merlinsoftware.databinding.ActivityDetailEpisodeBinding
import com.example.merlinsoftware.databinding.ItemRowBinding

class PodcastAdapter : RecyclerView.Adapter<PodcastAdapter.ViewHolder>() {

    private lateinit var binding: ItemRowBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRowBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Podcast.Feed){
            binding.apply {
                itemRowTitle.text = item.title.label
                itemRowAuthor.text = item.author.name.label
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Podcast.Feed>() {

        override fun areItemsTheSame(oldItem: Podcast.Feed, newItem: Podcast.Feed): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Podcast.Feed, newItem: Podcast.Feed): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}