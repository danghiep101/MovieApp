package com.example.movieapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.data.repository.MovieRepositoryImp
import com.example.movieapp.viewmodel.TvShowsViewModel

class TvShowsViewModelFactory(private val repositoryImp: MovieRepositoryImp): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TvShowsViewModel::class.java)){
            return TvShowsViewModel(repositoryImp) as T
        }
        throw IllegalArgumentException()
    }
}