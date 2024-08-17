package com.example.movieapp.data.resource

import com.example.movieapp.data.model.DetailModelResponse
import com.example.movieapp.data.model.ModelResponse
import com.example.movieapp.data.model.SearchMovieModelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {


    @GET("v1/api/danh-sach/phim-le?limit=64")
    suspend fun getMovies(
        @Query("page") page: Int, @Query("limit") limit: Int
    ): Response<ModelResponse>


    @GET("phim/{slug}")
    suspend fun getDetailMovie(@Path("slug") slug: String?): Response<DetailModelResponse>

    @GET("v1/api/danh-sach/phim-bo?limit = 64")
    suspend fun getMoviesSeries(
        @Query("page") page: Int, @Query("limit") limit: Int
    ): Response<ModelResponse>

    @GET("v1/api/danh-sach/hoat-hinh?limit = 64")
    suspend fun getCartoonMovies(
        @Query("page") page: Int, @Query("limit") limit: Int
    ): Response<ModelResponse>

    @GET("v1/api/danh-sach/tv-shows?limit = 64")
    suspend fun getTvShows(
        @Query("page") page: Int, @Query("limit") limit: Int
    ): Response<ModelResponse>

    @GET("v1/api/tim-kiem?keyword=")
    suspend fun getMovieFromSearching(@Query("keyword") keyword: String): Response<SearchMovieModelResponse>
}