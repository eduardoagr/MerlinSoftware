package com.example.merlinsoftware.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.merlinsoftware.databinding.ItemRowBinding
import com.example.merlinsoftware.model.Entry
import com.example.merlinsoftware.model.Feed

class PodcastAdapter : RecyclerView.Adapter<PodcastAdapter.ViewHolder>() {

    private lateinit var binding: ItemRowBinding
    private lateinit var context: Context
    private var podcastList = emptyList<Entry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRowBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = podcastList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return podcastList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Entry) {
            binding.apply {
                itemRowTitle.text = item.title.label
                itemRowAuthor.text = item.imArtist.label
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(podcasts: List<Entry>) {
        podcastList = podcasts
        notifyDataSetChanged()
    }
}
