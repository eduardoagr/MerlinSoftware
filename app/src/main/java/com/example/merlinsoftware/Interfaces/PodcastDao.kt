package com.example.merlinsoftware.Interfaces

import Podcast
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

    @Dao
    interface PodcastDao {
        @Query("SELECAT * FROM podcast")
        suspend fun getAll(): List<Podcast>

        //We need tis, because, when we get,we ned to store it somewhere
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertAll(podcasts: List<Podcast>)
    }
