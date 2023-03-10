package com.example.merlinsoftware.view

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.merlinsoftware.R
import com.example.merlinsoftware.databinding.ActivityDetailEpisodeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jdom2.input.SAXBuilder
import java.net.URL

class DetailEpisodeActivity : AppCompatActivity(),View.OnClickListener{

    private lateinit var binding: ActivityDetailEpisodeBinding
    private var mp3Url = ""
    private var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEpisodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val artist = intent.getStringExtra("artist")
        val desc = intent.getStringExtra("desc")
        val image = intent.getStringExtra("img")
        val feed = intent.getStringExtra("feed")


        // Load the episode details into the view
        binding.apply {
            Glide.with(applicationContext).load(image).override(600, 600).into(podcastDetailEpisodeImage)
            podcastDetailEpisodeTitle.text = title
            podcastDetailEpisodeAuthor.text = artist
            podcastDetailEpisodeDesc.text = desc ?: "We could not find a description"
            podcastDetailEpisodePlay.setOnClickListener(this@DetailEpisodeActivity)
            podcastDetailEpisodeStop.setOnClickListener(this@DetailEpisodeActivity)
        }

        // Fetch the MP3 URL for the current episode
        GlobalScope.launch {
            mp3Url = feed?.let { fetchMp3Url(it) }.toString()
            withContext(Dispatchers.Main) {
                Log.e("Edu", "onCreate: " + mp3Url )
            }
        }
    }



    private fun fetchMp3Url(feedUrl: String): String {
        val xml = URL(feedUrl).readText()
        val rss = SAXBuilder().build(xml.reader())
        var mp3Url = ""
        val entries = rss.rootElement.getChild("channel").getChildren("item")
        for (entry in entries) {
            val enclosure = entry.getChild("enclosure")
            if (enclosure != null && enclosure.getAttributeValue("type") == "audio/mpeg") {
                mp3Url = enclosure.getAttributeValue("url")
                break
            }
        }
        return mp3Url
    }

    override fun onClick(v: View?) {
       when(v?.id){
           R.id.podcast_detail_episode_play -> {
                   mediaPlayer = MediaPlayer().apply {
                       setDataSource(mp3Url)
                       setOnPreparedListener {
                           start()
                       }
                       prepareAsync()
                   }
               }
           R.id.podcast_detail_episode_stop -> {
               mediaPlayer?.stop()
               mediaPlayer?.release()
               mediaPlayer = null

           }
       }
    }
}