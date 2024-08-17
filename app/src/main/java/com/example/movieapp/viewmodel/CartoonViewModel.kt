package com.example.movieapp.viewmodel

import com.example.movieapp.data.model.ModelResponse.Data.Item
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.repository.MovieRepositoryImp
import com.example.movieapp.data.resource.Result
import kotlinx.coroutines.launch

class CartoonViewModel(private val repository: MovieRepository = MovieRepositoryImp()): ViewModel() {
    private  val _movie = MutableLiveData<List<Item>?>()
    val movie : LiveData<List<Item>?> = _movie

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading :LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Exception?>()
    val error : LiveData<Exception?> = _error

    private var currentPage = 1
    private var limit = 12

    fun  loadCartoon(){
        if (_isLoading.value == true) {
            return
        }
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.getCartoon(currentPage, limit)
            _isLoading.value = false
            if(result is Result.Success){
                _movie.postValue(result.data.data.items)
                _error.postValue(null)
                currentPage++
            }else if(result is Result.Error){
                _movie.postValue(null)
                _error.postValue(result.exception)
            }

        }

    }
}