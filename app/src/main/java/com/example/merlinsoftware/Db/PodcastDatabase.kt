package com.example.merlinsoftware.Db

import Podcast
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.merlinsoftware.Interfaces.PodcastDao

@Database(entities = [Podcast::class], version = 1)
abstract class PodcastDatabase : RoomDatabase() {

    abstract fun podcastDao(): PodcastDao

    companion object {
        @Volatile
        private var INSTANCE: PodcastDatabase? = null

        fun getDatabase(context: Context): PodcastDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PodcastDatabase::class.java,
                    "podcasts_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}