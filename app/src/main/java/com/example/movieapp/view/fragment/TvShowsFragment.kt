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
import com.example.movieapp.databinding.FragmentTvShowsBinding
import com.example.movieapp.utils.Extension
import com.example.movieapp.view.adapter.MoviesRecyclerViewAdapter
import com.example.movieapp.view.detail.DetailMovieActivity
import com.example.movieapp.viewmodel.TvShowsViewModel
import com.example.movieapp.viewmodel.factory.TvShowsViewModelFactory

class TvShowsFragment : Fragment(), MoviesRecyclerViewAdapter.OnItemClickListener {
    private lateinit var binding: FragmentTvShowsBinding
    private lateinit var moviesAdapter: MoviesRecyclerViewAdapter

    private val viewModel: TvShowsViewModel by viewModels {
        TvShowsViewModelFactory(MovieRepositoryImp())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTvShowsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeViewModel()
        return binding.root
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
                        viewModel.loadTvShows()
                    }
                }

            })
        }
    }

    private fun observeViewModel() {
        viewModel.movies.observe(viewLifecycleOwner) { movieList ->
            Log.d("MoviesFragment", "movieList observed: $movieList")
            if (movieList != null) {
                moviesAdapter.addMovies(movieList)
            } else {
                Log.d("MoviesFragment", "movieList is null")
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            Log.d("MoviesFragment", "isLoading observed: $isLoading")
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { exception ->
            if (exception != null) {
                Log.d("MoviesFragment", "Error observed: ${exception.message}")
                Extension.showToast(requireContext(), exception.message ?: "Unknown error")
            } else {
                Log.d("MoviesFragment", "Error is null")
            }
        }
        viewModel.loadTvShows()
    }

    override fun onItemClick(movie: ModelResponse.Data.Item) {
        val intent = Intent(requireContext(), DetailMovieActivity::class.java)
        intent.putExtra("SLUG", movie.slug)
        intent.putExtra("GENRE", movie.type)
        startActivity(intent)
    }
}