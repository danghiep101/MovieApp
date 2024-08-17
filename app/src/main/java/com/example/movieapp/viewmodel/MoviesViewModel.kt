package com.example.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.ModelResponse.Data.Item
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.repository.MovieRepositoryImp
import com.example.movieapp.data.resource.Result
import kotlinx.coroutines.launch

class MoviesViewModel(private val movieRepository: MovieRepository = MovieRepositoryImp()) : ViewModel() {

    private val _movies = MutableLiveData<List<Item>?>()
    val movies: LiveData<List<Item>?> get() = _movies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Exception?>()
    val error: LiveData<Exception?> get() = _error

    private var currentPage = 1
    private var limit = 12

    fun loadMovies() {
        if (_isLoading.value == true) {
            return
        }
        _isLoading.value = true
        viewModelScope.launch {
                val result = movieRepository.getMovie(currentPage, limit)
            _isLoading.value = false
            if(result is Result.Success){
                    _movies.postValue(result.data.data.items)
                    _error.postValue(null)
                    currentPage++
                }else if(result is Result.Error){
                    _movies.postValue(null)
                    _error.postValue(result.exception)
                }

        }
    }
}
