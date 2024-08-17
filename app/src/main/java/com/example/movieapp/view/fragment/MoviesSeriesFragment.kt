package com.example.movieapp.view.fragment

import GridSpacingItemDecoration
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.ModelResponse
import com.example.movieapp.data.repository.MovieRepositoryImp
import com.example.movieapp.databinding.FragmentMoviesSeriesBinding
import com.example.movieapp.utils.Extension
import com.example.movieapp.view.adapter.MoviesRecyclerViewAdapter
import com.example.movieapp.view.detail.DetailMovieActivity
import com.example.movieapp.viewmodel.MovieSeriesViewModel
import com.example.movieapp.viewmodel.factory.MovieSeriesViewModelFactory


class MoviesSeriesFragment : Fragment(), MoviesRecyclerViewAdapter.OnItemClickListener {
    private lateinit var binding: FragmentMoviesSeriesBinding
    private lateinit var moviesAdapter: MoviesRecyclerViewAdapter
    private val viewModel: MovieSeriesViewModel by viewModels {
        MovieSeriesViewModelFactory(MovieRepositoryImp())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesSeriesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.movie.observe(viewLifecycleOwner) { movieList ->
            if (movieList != null) {
                moviesAdapter.addMovies(movieList)
            } else {
                Log.d("MoviesSeriesFragment", "movieList is null")
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { exception ->
            if (exception != null) {
                Log.d("MoviesFragment", "Error observed: ${exception.message}")
                Extension.showToast(requireContext(), exception.message ?: "Unknown error")
            } else {
                Log.d("MoviesFragment", "Error is null")
            }
        }

        viewModel.loadMovieSeries()
    }

    private fun setupRecyclerView() {
        moviesAdapter = MoviesRecyclerViewAdapter(ArrayList(), this)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = moviesAdapter
            addItemDecoration(GridSpacingItemDecoration(3, 25, true))

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (!(viewModel.isLoading.value ?: false) &&
                        (visibleItemCount + pastVisibleItems) >= totalItemCount
                    ) {
                        viewModel.loadMovieSeries()
                    }
                }

            })
        }
    }

    override fun onItemClick(movie: ModelResponse.Data.Item) {
        val intent = Intent(requireContext(), DetailMovieActivity::class.java)
        intent.putExtra("SLUG", movie.slug)
        intent.putExtra("GENRE", movie.type)
        startActivity(intent)
    }


}