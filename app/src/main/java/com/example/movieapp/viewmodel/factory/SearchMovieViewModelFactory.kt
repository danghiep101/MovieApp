package com.example.movieapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.data.repository.MovieRepositoryImp
import com.example.movieapp.viewmodel.SearchMovieViewModel

class SearchMovieViewModelFactory(
    private val repository: MovieRepositoryImp
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchMovieViewModel::class.java)) {
            return SearchMovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}