package com.example.movieapp.view.searchmovie

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.R
import com.example.movieapp.data.model.SearchMovieModelResponse
import com.example.movieapp.data.repository.MovieRepositoryImp
import com.example.movieapp.databinding.ActivitySearchMovieBinding
import com.example.movieapp.view.adapter.SearchViewAdapter
import com.example.movieapp.view.detail.DetailMovieActivity
import com.example.movieapp.viewmodel.SearchMovieViewModel
import com.example.movieapp.viewmodel.factory.SearchMovieViewModelFactory

class SearchMovieActivity : AppCompatActivity(), SearchViewAdapter.OnItemClickListener {
    private lateinit var binding: ActivitySearchMovieBinding
    private val viewModel: SearchMovieViewModel by viewModels {
        SearchMovieViewModelFactory(MovieRepositoryImp())
    }
    private lateinit var adapter: SearchViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        ObserveViewModel()
        binding.btnSearch.setOnClickListener {
            val keyword = binding.editTextText.text.toString()
            if (keyword.isEmpty()) {
                binding.editTextText.error = "Movie's name is required"
            } else {
                viewModel.loadMovie(keyword)
            }

        }
    }

    private fun setUpRecyclerView() {
        adapter = SearchViewAdapter(this, ArrayList(), this)
        binding.gridView.adapter = adapter
    }


    private fun ObserveViewModel() {
        viewModel.movies.observe(this) { movies ->
            if (movies != null) {
                adapter.updateMovies(movies)
            }
        }
    }

    override fun onItemClick(movie: SearchMovieModelResponse.Data.Item) {
        val intent = Intent(this@SearchMovieActivity, DetailMovieActivity::class.java)
        intent.putExtra("SLUG", movie.slug)
        intent.putExtra("GENRE", movie.type)
        startActivity(intent)
    }

}