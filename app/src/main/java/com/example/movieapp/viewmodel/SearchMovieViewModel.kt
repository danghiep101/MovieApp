package com.example.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.SearchMovieModelResponse.Data.Item
import com.example.movieapp.data.repository.MovieRepositoryImp
import com.example.movieapp.data.resource.Result
import kotlinx.coroutines.launch

class SearchMovieViewModel(private val repositoryImp: MovieRepositoryImp): ViewModel() {
    private val _movies = MutableLiveData<List<Item>?>()
    val movies : LiveData<List<Item>?> = _movies


    fun loadMovie(keyword: String){
        viewModelScope.launch {
            val result = repositoryImp.getMovieFromSearching(keyword)
            if(result is Result.Success){
                _movies.postValue(result.data.data.items)
            }else if(result is Result.Error){
                _movies.postValue(null)
            }
        }
    }

}