package com.example.movieapp.data.repository

import com.example.movieapp.data.model.DetailModelResponse
import com.example.movieapp.data.model.ModelResponse
import com.example.movieapp.data.model.SearchMovieModelResponse
import com.example.movieapp.data.resource.Result

interface MovieRepository {
    suspend fun getMovie(page: Int, limit: Int): Result<ModelResponse>
    suspend fun getMovieDetail(slug: String): Result<DetailModelResponse>
    suspend fun getMovieSeries(page: Int, limit: Int): Result<ModelResponse>

    suspend fun getCartoon(page: Int, limit: Int): Result<ModelResponse>
    suspend fun getTvShows(page: Int, limit: Int): Result<ModelResponse>

    suspend fun getMovieFromSearching(keyword: String): Result<SearchMovieModelResponse>

}