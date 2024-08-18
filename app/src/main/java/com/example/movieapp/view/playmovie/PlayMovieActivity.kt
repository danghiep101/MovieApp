package com.example.movieapp.view.playmovie

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.datasource.DefaultHttpDataSource
import com.example.movieapp.databinding.ActivityPlayMovieBinding
import com.example.movieapp.viewmodel.PlayMovieViewModel

class PlayMovieActivity : AppCompatActivity() {
    private var binding: ActivityPlayMovieBinding? = null
    private var player: ExoPlayer? = null
    private val viewModel: PlayMovieViewModel by viewModels()

    private var currentPosition: Long = 0L
    private var isPlaying: Boolean = true

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayMovieBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val linkPlayMovie = intent.getStringExtra("LINK_PLAY_MOVIE")

        if (linkPlayMovie != null) {
            viewModel.setMovieData(linkPlayMovie)
            setupPlayer(linkPlayMovie)
        }
        viewModel.linkPlayMovie.observe(this) { link ->
            player?.setMediaSource(
                HlsMediaSource.Factory(DefaultHttpDataSource.Factory())
                    .createMediaSource(MediaItem.fromUri(link))
            )
            player?.prepare()
            player?.playWhenReady = isPlaying
        }

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getLong("CURRENT_POSITION", 0L)
            isPlaying = savedInstanceState.getBoolean("IS_PLAYING", true)
            player?.prepare()
            player?.playWhenReady = true
            player?.seekTo(currentPosition)
        }
        hideSystemUI()
    }


    @OptIn(UnstableApi::class)
    private fun setupPlayer(linkPlayMovie: String) {
        player = ExoPlayer.Builder(this).build()
        binding?.playerView?.player = player
        val dataSourceFactory = DefaultHttpDataSource.Factory()
        player?.seekTo(currentPosition)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("CURRENT_POSITION", player?.currentPosition!!)
        outState.putBoolean("IS_PLAYING", player?.playWhenReady!!)

    }

    override fun onStart() {
        super.onStart()
        player?.playWhenReady = isPlaying

    }

    override fun onStop() {
        super.onStop()
        isPlaying = player?.playWhenReady!!
        player?.playWhenReady = false

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun hideSystemUI() {

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }
}

