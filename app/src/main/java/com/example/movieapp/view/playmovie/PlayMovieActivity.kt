package com.example.movieapp.view.playmovie

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.data.model.DetailModelResponse.Episode.ServerData
import com.example.movieapp.databinding.ActivityPlayMovieBinding
import com.example.movieapp.view.adapter.SeriesRecyclerViewAdapter
import com.example.movieapp.viewmodel.PlayMovieViewModel

class PlayMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayMovieBinding
    private lateinit var player: ExoPlayer
    private val viewModel: PlayMovieViewModel by viewModels()

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linkPlayMovie = intent.getStringExtra("LINK_PLAY_MOVIE")

        if (linkPlayMovie != null) {
            viewModel.setMovieData(linkPlayMovie)
        }

        viewModel.linkPlayMovie.observe(this) { link ->
            setupPlayer(link)
        }

    }

    @UnstableApi
    private fun setupPlayer(linkPlayMovie: String) {
        if (!::player.isInitialized) {
            player = ExoPlayer.Builder(this).build()
        }
        binding.playerView.player = player
        val dataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaSource: MediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(linkPlayMovie))

        player.setMediaSource(mediaSource)
        player.prepare()
        player.playWhenReady = true
    }


    override fun onStart() {
        super.onStart()
        if (::player.isInitialized) {
            player.playWhenReady = true
        }
    }

    override fun onStop() {
        super.onStop()
        if (::player.isInitialized) {
            player.playWhenReady = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::player.isInitialized) {
            player.release()
        }
    }
}
