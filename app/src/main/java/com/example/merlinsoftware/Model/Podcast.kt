import java.util.*

data class Podcast(
    val id: String,
    val title: String,
    val author: String,
    val summary: String,
    val imageUrl: String,
    val releaseDate: Date,
    val audioUrl: String,
    var fetchTime: Long = 0
)

data class PodcastListJson(
    val feed: PodcastFeedJson
)

data class PodcastFeedJson(
    val entry: List<PodcastEntryJson>
)

data class PodcastEntryJson(
    val id: String,
    val title: PodcastTitleJson,
    val artist: PodcastAuthorJson,
    val summary: PodcastSummaryJson?,
    val image: PodcastImageJson?,
    val releaseDate: PodcastReleaseDateJson?,
    val content: List<PodcastContentJson>?
)

data class PodcastTitleJson(
    val label: String
)

data class PodcastAuthorJson(
    val label: String
)

data class PodcastSummaryJson(
    val label: String
)

data class PodcastImageJson(
    val url: String
)

data class PodcastReleaseDateJson(
    val label: String
)

data class PodcastContentJson(
    val url: String
)