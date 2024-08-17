package com.example.movieapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.data.repository.MovieRepositoryImp
import com.example.movieapp.viewmodel.MoviesViewModel

class MoviesViewModelFactory(private val movieRepository: MovieRepositoryImp) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(movieRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
