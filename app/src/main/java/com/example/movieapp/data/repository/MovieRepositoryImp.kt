package com.example.movieapp.data.repository

import android.util.Log
import com.example.movieapp.data.model.DetailModelResponse
import com.example.movieapp.data.model.ModelResponse
import com.example.movieapp.data.model.SearchMovieModelResponse
import com.example.movieapp.data.resource.MovieApi
import com.example.movieapp.data.resource.Result
import com.example.movieapp.data.resource.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.E

class MovieRepositoryImp() : MovieRepository {
    val movieApi = RetrofitHelper.getInstance().create(MovieApi::class.java)
    override suspend fun getMovie(page: Int, limit: Int): Result<ModelResponse> {
        return withContext(Dispatchers.IO) {


            val response = movieApi.getMovies(page, limit)
            if (response.isSuccessful) {
                Log.d("MovieRepositoryImp", "Raw Response Body: ${response}")
                Result.Success(response.body()!!)
            } else {
                Log.d("MovieRepositoryImp", "Exception: ${response}")
                Result.Error(Exception(response.message()))
            }
        }
    }

    override suspend fun getMovieDetail(slug: String): Result<DetailModelResponse> {
        return try {
            val response = movieApi.getDetailMovie(slug)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error(Exception("Empty response body"))
                }
            } else {
                Result.Error(Exception("Error: ${response.message()}"))
            }
        } catch (e: IOException) {
            Result.Error(Exception("Network error: ${e.message}"))
        } catch (e: HttpException) {
            Result.Error(Exception("HTTP error: ${e.message}"))
        }
    }

    override suspend fun getMovieSeries(page: Int, limit: Int): Result<ModelResponse> {
        return try {
            val response = movieApi.getMoviesSeries(page, limit)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error(Exception("Empty response body"))
                }
            } else {
                Result.Error(Exception("Response is Failed"))
            }
        } catch (e: Exception) {
            Result.Error(Exception("Error: ${e.message}"))
        }
    }

    override suspend fun getCartoon(page: Int, limit: Int): Result<ModelResponse> {
        return try {
            val response = movieApi.getCartoonMovies(page, limit)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error(Exception("Empty response"))
                }
            } else {
                Result.Error(Exception("Response is failed"))
            }
        } catch (e: Exception) {
            Result.Error(Exception("Error: ${e.message}"))
        }
    }

    override suspend fun getTvShows(page: Int, limit: Int): Result<ModelResponse> {
        return try {
            val response = movieApi.getTvShows(page, limit)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error(Exception("Empty response"))
                }
            } else {
                Result.Error(Exception("Response is failed"))
            }
        } catch (e: Exception) {
            Result.Error(Exception("Error: ${e.message}"))
        }
    }

    override suspend fun getMovieFromSearching(keyword: String): Result<SearchMovieModelResponse> {
        return try {
            val response = movieApi.getMovieFromSearching(keyword)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error(Exception("body is null"))
                }
            } else {
                Result.Error(Exception("response is failed"))
            }
        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }
    }


}
