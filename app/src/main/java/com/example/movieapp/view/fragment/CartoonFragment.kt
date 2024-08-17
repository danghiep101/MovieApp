package com.example.movieapp.view.fragment

import GridSpacingItemDecoration
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.ModelResponse
import com.example.movieapp.data.repository.MovieRepositoryImp
import com.example.movieapp.databinding.FragmentCartoonBinding
import com.example.movieapp.utils.Extension
import com.example.movieapp.view.adapter.MoviesRecyclerViewAdapter
import com.example.movieapp.view.detail.DetailMovieActivity
import com.example.movieapp.viewmodel.CartoonViewModel
import com.example.movieapp.viewmodel.factory.CartoonViewModelFactory


class CartoonFragment : Fragment(), MoviesRecyclerViewAdapter.OnItemClickListener {
    private lateinit var binding: FragmentCartoonBinding
    private lateinit var movieAdapter: MoviesRecyclerViewAdapter
    private val viewModel: CartoonViewModel by viewModels {
        CartoonViewModelFactory(MovieRepositoryImp())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartoonBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.movie.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                movieAdapter.addMovies(movies)
            } else {
                Log.d("CartoonFragment", "movies is null")
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Extension.showToast(requireContext(), "error is null")
            }
        }
        viewModel.loadCartoon()
    }

    private fun setupRecyclerView() {
        movieAdapter = MoviesRecyclerViewAdapter(ArrayList(), this)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = movieAdapter
            addItemDecoration(GridSpacingItemDecoration(3, 25, true))

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val visibleItems = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()

                    if (viewModel.isLoading.value != true
                        && (visibleItems + pastVisibleItem) >= totalItemCount) {
                        viewModel.loadCartoon()
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