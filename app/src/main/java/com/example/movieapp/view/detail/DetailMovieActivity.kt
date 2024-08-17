package com.example.movieapp.view.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.data.model.DetailModelResponse
import com.example.movieapp.data.model.DetailModelResponse.Episode.ServerData
import com.example.movieapp.databinding.ActivityDetailMovieBinding
import com.example.movieapp.view.adapter.SeriesRecyclerViewAdapter
import com.example.movieapp.view.playmovie.PlayMovieActivity
import com.example.movieapp.viewmodel.DetailMovieViewModel


class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private val viewModel: DetailMovieViewModel  = DetailMovieViewModel()
    private var slug: String? = null
    private var movieType: String? = null
    private lateinit var adapter: SeriesRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        slug = intent.getStringExtra("SLUG")
        movieType = intent.getStringExtra("GENRE")

        if (slug != null) {
            observeViewModel()
            viewModel.loadMovieDetail(slug!!)
        }

    }

    private fun observeViewModel() {
        viewModel.movieDetail.observe(this) { detailModelResponse ->
            if (detailModelResponse != null) {
                bindData(detailModelResponse)
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.error.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.episodes.observe(this) { episodes ->
            setupRecyclerView(episodes)
        }

    }

    private fun setupRecyclerView(episodes: List<ServerData>) {
        Log.d("DetailMovieActivity", "Adapter Data: $episodes")
        adapter = SeriesRecyclerViewAdapter(episodes) { episode ->
            val intent = Intent(this@DetailMovieActivity, PlayMovieActivity::class.java).apply {
                putExtra("LINK_PLAY_MOVIE", episode.linkM3u8)
                putExtra("MOVIE_TYPE", movieType)
            }
            startActivity(intent)
        }
        binding.recyclerViewSeries.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSeries.adapter = adapter
    }



    private fun bindData(detailModelResponse: DetailModelResponse) {
        val episode = detailModelResponse.episodes.firstOrNull()
        val linkM3u8 = episode?.serverData?.firstOrNull()?.linkM3u8
        Glide.with(this)
            .load(detailModelResponse.movie.thumbUrl)
            .into(binding.imagePoster)
        binding.textName.text = detailModelResponse.movie.name
        binding.textYear.text = detailModelResponse.movie.year.toString()
        binding.textContent.text = detailModelResponse.movie.content
        binding.textQuality.text = detailModelResponse.movie.quality
        binding.textTime.text = detailModelResponse.movie.time
        binding.textLang.text = detailModelResponse.movie.lang
        viewModel.setMovieData(detailModelResponse.episodes.flatMap { it.serverData })
        binding.btnPlay.setOnClickListener {
            if (linkM3u8 != null) {
                val intent = Intent(this@DetailMovieActivity, PlayMovieActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("LINK_PLAY_MOVIE", linkM3u8)
                }
                startActivity(intent)
            } else {
                Log.d("CheckLinkEmbed", "LinkEmbed is null")
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarText.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.progressBarImage.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
