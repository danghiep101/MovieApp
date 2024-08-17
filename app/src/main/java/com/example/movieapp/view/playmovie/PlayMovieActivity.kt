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
import com.example.movieapp.databinding.ActivityPlayMovieBinding
import com.example.movieapp.viewmodel.PlayMovieViewModel

class PlayMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayMovieBinding
    private lateinit var player: ExoPlayer
    private val viewModel: PlayMovieViewModel by viewModels()

    private var currentPosition: Long = 0L
    private var isPlaying: Boolean = true

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
            if (!::player.isInitialized) {
                setupPlayer(link)
            } else {
                player.setMediaSource(
                    HlsMediaSource.Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(link))
                )
                player.prepare()
                player.playWhenReady = isPlaying
            }
            player.seekTo(currentPosition)
        }


        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getLong("CURRENT_POSITION", 0L)
            isPlaying = savedInstanceState.getBoolean("IS_PLAYING", true)
        }
        hideSystemUI()
    }

    @OptIn(UnstableApi::class)
    private fun setupPlayer(linkPlayMovie: String) {
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player
        val dataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaSource: MediaSource = HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(linkPlayMovie))

        player.setMediaSource(mediaSource)
        player.prepare()
        player.playWhenReady = isPlaying
        player.seekTo(currentPosition)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::player.isInitialized) {
            outState.putLong("CURRENT_POSITION", player.currentPosition)
            outState.putBoolean("IS_PLAYING", player.playWhenReady)
        }
    }

    override fun onStart() {
        super.onStart()
        if (::player.isInitialized) {
            player.playWhenReady = isPlaying
        }
    }

    override fun onStop() {
        super.onStop()
        if (::player.isInitialized) {
            isPlaying = player.playWhenReady
            player.playWhenReady = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::player.isInitialized) {
            player.release()
        }
    }
    private fun hideSystemUI() {

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }
}
