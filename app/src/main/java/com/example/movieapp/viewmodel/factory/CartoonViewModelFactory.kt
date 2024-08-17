package com.example.movieapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.repository.MovieRepositoryImp
import com.example.movieapp.viewmodel.CartoonViewModel

class CartoonViewModelFactory(private val repository: MovieRepositoryImp): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CartoonViewModel::class.java)){
            return CartoonViewModel(repository) as T
        }else{
            throw IllegalArgumentException()
        }
    }
}