package com.example.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.DetailModelResponse.Episode.ServerData
import kotlinx.coroutines.launch

class PlayMovieViewModel : ViewModel() {
    private val _linkPlayMovie = MutableLiveData<String>()
    val linkPlayMovie: LiveData<String> = _linkPlayMovie

    fun setMovieData(linkPlayMovie: String) {
        _linkPlayMovie.value = linkPlayMovie
    }

}
