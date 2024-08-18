package com.example.movieapp.view.playmovie

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
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
    private var isPlaying: Boolean = false
    private var linkPlayMovie : String = ""
    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayMovieBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        linkPlayMovie = intent.getStringExtra("LINK_PLAY_MOVIE").toString()

        setupPlayer(linkPlayMovie)

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getLong("CURRENT_POSITION", 0L)
            isPlaying = savedInstanceState.getBoolean("IS_PLAYING", true)
            player?.seekTo(currentPosition)
            player?.playWhenReady = isPlaying
        }

        hideSystemUI()
    }


    @OptIn(UnstableApi::class)
    private fun setupPlayer(linkPlayMovie: String) {
        player = ExoPlayer.Builder(this).build()

        player?.setMediaSource(
            HlsMediaSource.Factory(DefaultHttpDataSource.Factory())
                .createMediaSource(MediaItem.fromUri(linkPlayMovie))
        )
        player?.addListener(object: Player.Listener{
            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                super.onPlayWhenReadyChanged(playWhenReady, reason)
                isPlaying = playWhenReady
            }
        })
        player?.prepare()
        player?.playWhenReady = true
        binding?.playerView?.player = player

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("CURRENT_POSITION", player?.currentPosition?: 0L)
        outState.putBoolean("IS_PLAYING", player?.playWhenReady?: true)
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }
    private fun hideSystemUI() {

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }
}

