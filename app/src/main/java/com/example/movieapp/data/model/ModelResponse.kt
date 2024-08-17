package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class ModelResponse(
    val data: Data,
    val msg: String,
    val status: String
) {
    data class Data(
        val APP_DOMAIN_CDN_IMAGE: String,
        val APP_DOMAIN_FRONTEND: String,
        val breadCrumb: List<BreadCrumb>,
        val items: List<Item>,
        val params: Params,
        val seoOnPage: SeoOnPage,
        val titlePage: String,
        val type_list: String
    ) {
        data class BreadCrumb(
            val isCurrent: Boolean,
            val name: String,
            val position: Int,
            val slug: String
        )

        data class Item(
            @SerializedName("_id") val _id: String,
            val category: List<Category>,
            val chieurap: Boolean,
            val country: List<Country>,
            @SerializedName("episode_current") val episode_current: String,
            val lang: String,
            val modified: Modified,
            @SerializedName("name") val name: String,
            val origin_name: String,
            @SerializedName("poster_url") val poster_url: String,
            val quality: String,
            @SerializedName("slug") val slug: String,
            val sub_docquyen: Boolean,
            val thumb_url: String,
            val time: String,
            val type: String,
            val year: Int
        ) {
            data class Category(
                val id: String,
                val name: String,
                val slug: String
            )

            data class Country(
                val id: String,
                val name: String,
                val slug: String
            )

            data class Modified(
                val time: String
            )
        }

        data class Params(
            val filterCategory: List<String>,
            val filterCountry: List<String>,
            val filterType: String,
            val filterYear: String,
            val pagination: Pagination,
            val sortField: String,
            val sortType: String,
            val type_slug: String
        ) {
            data class Pagination(
                val currentPage: Int,
                val totalItems: Int,
                val totalItemsPerPage: Int,
                val totalPages: Int
            )
        }

        data class SeoOnPage(
            val descriptionHead: String,
            val og_image: List<String>,
            val og_type: String,
            val og_url: String,
            val titleHead: String
        )
    }
}