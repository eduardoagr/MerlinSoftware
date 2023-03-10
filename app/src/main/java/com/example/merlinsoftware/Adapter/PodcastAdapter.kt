package com.example.merlinsoftware.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.merlinsoftware.R
import com.example.merlinsoftware.databinding.ItemRowBinding
import com.example.merlinsoftware.model.Entry
import com.example.merlinsoftware.view.DetailPodcastActivity
import java.util.*

class PodcastAdapter(private val context: Context) : RecyclerView.Adapter<PodcastAdapter.ViewHolder>(), Filterable {

    private var podcastList = mutableListOf<Entry>()
    private var filteredPodcastList = mutableListOf<Entry>()

    init {
        filteredPodcastList = podcastList
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Entry>) {
        podcastList.clear()
        podcastList.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(context: Context, podcast: Entry) {
            binding.apply {
                itemRowTitle.text = "Title " + podcast.title.label
                itemRowAuthor.text = "Artist: " + podcast.imName.label

                root.setOnClickListener{
                    val intent = Intent(context, DetailPodcastActivity::class.java)
                    //required
                    intent.putExtra("id", podcast.id.attributes.imId)

                    //additional properties
                    intent.putExtra("title", podcast.title.label)
                    intent.putExtra("Author", podcast.imName.label)
                    intent.putExtra("desc", podcast.summary.label)

                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, filteredPodcastList[position])
    }

    override fun getItemCount() = filteredPodcastList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredPodcastList = podcastList
                } else {
                    val resultList = mutableListOf<Entry>()
                    for (row in podcastList) {
                        if (row.title.label.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))
                            || row.imName.label.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    filteredPodcastList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredPodcastList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredPodcastList = results?.values as MutableList<Entry>
                notifyDataSetChanged()
            }
        }
    }
}
