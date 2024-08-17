package com.example.movieapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class DetailModelResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("movie")
    val movie: Movie,
    @SerializedName("episodes")
    val episodes: List<Episode>
) {
    data class Movie(
        @SerializedName("created")
        val created: Time,
        @SerializedName("modified")
        val modified: Time,
        @SerializedName("_id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("slug")
        val slug: String,
        @SerializedName("origin_name")
        val originName: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("poster_url")
        val posterUrl: String,
        @SerializedName("thumb_url")
        val thumbUrl: String,
        @SerializedName("is_copyright")
        val isCopyright: Boolean,
        @SerializedName("sub_docquyen")
        val subDocQuyen: Boolean,
        @SerializedName("chieurap")
        val chieurap: Boolean,
        @SerializedName("trailer_url")
        val trailerUrl: String,
        @SerializedName("time")
        val time: String,
        @SerializedName("episode_current")
        val episodeCurrent: String,
        @SerializedName("episode_total")
        val episodeTotal: String,
        @SerializedName("quality")
        val quality: String,
        @SerializedName("lang")
        val lang: String,
        @SerializedName("notify")
        val notify: String,
        @SerializedName("showtimes")
        val showtimes: String,
        @SerializedName("year")
        val year: Int,
        @SerializedName("view")
        val view: Int,
        @SerializedName("actor")
        val actor: List<String>,
        @SerializedName("director")
        val director: List<String>,
        @SerializedName("category")
        val category: List<Category>,
        @SerializedName("country")
        val country: List<Country>
    )

    data class Time(
        @SerializedName("time")
        val time: String
    )

    data class Category(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("slug")
        val slug: String
    )

    data class Country(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("slug")
        val slug: String
    )

    data class Episode(
        @SerializedName("server_name")
        val serverName: String,
        @SerializedName("server_data")
        val serverData: List<ServerData>
    ) {
        @Parcelize
        data class ServerData(
            @SerializedName("name")
            val name: String,
            @SerializedName("slug")
            val slug: String,
            @SerializedName("filename")
            val filename: String,
            @SerializedName("link_embed")
            val linkEmbed: String,
            @SerializedName("link_m3u8")
            val linkM3u8: String
        ): Parcelable
    }
}
