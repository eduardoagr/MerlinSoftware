package com.example.merlinsoftware.model

data class PodcastEpisodeDetail(
    val resultCount: Long,
    val results: List<EpisodeInfo>,
)
    data class EpisodeInfo(
        val wrapperType: String,
        val kind: String,
        val artistId: Long?,
        val collectionId: Long,
        val trackId: Long,
        val artistName: String?,
        val collectionName: String,
        val trackName: String,
        val collectionCensoredName: String?,
        val trackCensoredName: String?,
        val artistViewUrl: String,
        val collectionViewUrl: String,
        val feedUrl: String,
        val trackViewUrl: String,
        val artworkUrl30: String?,
        val artworkUrl60: String,
        val artworkUrl100: String?,
        val collectionPrice: Double?,
        val trackPrice: Double?,
        val collectionHdPrice: Long?,
        val releaseDate: String,
        val collectionExplicitness: String?,
        val trackExplicitness: String?,
        val trackCount: Long?,
        val trackTimeMillis: Long,
        val country: String,
        val currency: String?,
        val primaryGenreName: String?,
        val contentAdvisoryRating: String,
        val artworkUrl600: String,
        val genreIds: List<String>?,
        val genres: List<Any?>,
        val episodeUrl: String?,
        val closedCaptioning: String?,
        val artistIds: List<Long>?,
        val description: String?,
        val shortDescription: String?,
        val artworkUrl160: String?,
        val episodeFileExtension: String?,
        val episodeContentType: String?,
        val previewUrl: String?,
        val episodeGuid: String?,
    )
