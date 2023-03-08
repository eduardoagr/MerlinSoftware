package com.example.merlinsoftware.Model

import com.fasterxml.jackson.annotation.JsonProperty

data class Podcast(
    val feed: List<Feed>
)
{
    data class Feed(
        val author: Author,
        val entry: List<Entry>,
        val updated: Updated,
        val rights: Rights2,
        val title: Title2,
        val icon: Icon,
        val link: List<Link2>,
        val id: Id2,
    )

    data class Author(
        val name: Name,
        val uri: Uri,
    )

    data class Name(
        val label: String,
    )

    data class Uri(
        val label: String,
    )

    data class Entry(
        @JsonProperty("im:name")
        val imName: ImName,
        @JsonProperty("im:image")
        val imImage: List<ImImage>,
        val summary: Summary,
        @JsonProperty("im:price")
        val imPrice: ImPrice,
        @JsonProperty("im:contentType")
        val imContentType: ImContentType,
        val rights: Rights?,
        val title: Title,
        val link: Link,
        val id: Id,
        @JsonProperty("im:artist")
        val imArtist: ImArtist,
        val category: Category,
        @JsonProperty("im:releaseDate")
        val imReleaseDate: ImReleaseDate,
    )

    data class ImName(
        val label: String,
    )

    data class ImImage(
        val label: String,
        val attributes: Attributes,
    )

    data class Attributes(
        val height: String,
    )

    data class Summary(
        val label: String,
    )

    data class ImPrice(
        val label: String,
        val attributes: Attributes2,
    )

    data class Attributes2(
        val amount: String,
        val currency: String,
    )

    data class ImContentType(
        val attributes: Attributes3,
    )

    data class Attributes3(
        val term: String,
        val label: String,
    )

    data class Rights(
        val label: String,
    )

    data class Title(
        val label: String,
    )

    data class Link(
        val attributes: Attributes4,
    )

    data class Attributes4(
        val rel: String,
        val type: String,
        val href: String,
    )

    data class Id(
        val label: String,
        val attributes: Attributes5,
    )

    data class Attributes5(
        @JsonProperty("im:id")
        val imId: String,
    )

    data class ImArtist(
        val label: String,
        val attributes: Attributes6?,
    )

    data class Attributes6(
        val href: String,
    )

    data class Category(
        val attributes: Attributes7,
    )

    data class Attributes7(
        @JsonProperty("im:id")
        val imId: String,
        val term: String,
        val scheme: String,
        val label: String,
    )

    data class ImReleaseDate(
        val label: String,
        val attributes: Attributes8,
    )

    data class Attributes8(
        val label: String,
    )

    data class Updated(
        val label: String,
    )

    data class Rights2(
        val label: String,
    )

    data class Title2(
        val label: String,
    )

    data class Icon(
        val label: String,
    )

    data class Link2(
        val attributes: Attributes9,
    )

    data class Attributes9(
        val rel: String,
        val type: String?,
        val href: String,
    )

    data class Id2(
        val label: String
    )
}