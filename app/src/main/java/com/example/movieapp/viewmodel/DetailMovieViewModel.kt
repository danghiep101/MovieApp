package com.example.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.DetailModelResponse
import com.example.movieapp.data.model.DetailModelResponse.Episode.ServerData
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.repository.MovieRepositoryImp
import com.example.movieapp.data.resource.Result
import kotlinx.coroutines.launch

class DetailMovieViewModel(
    private val movieRepository: MovieRepository = MovieRepositoryImp(),
) : ViewModel() {

    private val _movieDetail = MutableLiveData<DetailModelResponse?>()
    val movieDetail: LiveData<DetailModelResponse?> get() = _movieDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _episodes = MutableLiveData<List<ServerData>>()
    val episodes: LiveData<List<ServerData>> = _episodes

    fun loadMovieDetail(slug: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = movieRepository.getMovieDetail(slug)
            _isLoading.value = false
            if (result is Result.Success) {
                _movieDetail.postValue(result.data)
                _error.postValue(null)
            } else if (result is Result.Error) {
                _movieDetail.postValue(null)
                _error.postValue(result.exception.message)
            }
        }
    }

    fun setMovieData(episodes: List<ServerData>) {
        _episodes.value = episodes
    }


}
