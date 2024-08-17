package com.example.movieapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.data.repository.MovieRepositoryImp
import com.example.movieapp.viewmodel.MovieSeriesViewModel

class MovieSeriesViewModelFactory(private val repositoryImp: MovieRepositoryImp): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieSeriesViewModel::class.java)){
            return MovieSeriesViewModel(repositoryImp) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}