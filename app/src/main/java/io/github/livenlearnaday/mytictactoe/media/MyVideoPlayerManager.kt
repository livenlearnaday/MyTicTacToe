package io.github.livenlearnaday.mytictactoe.media

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_OFF
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView

object MyVideoPlayerManager {

    private const val TAG = "MyVideoPlayerManager"
    private var exoPlayer: ExoPlayer? = null

    private const val START_PLAYBACK_POSITION = 0L


    fun initializePlayer(playerView: PlayerView, context: Context) {
        exoPlayer = ExoPlayer
            .Builder(context)
            .build()
        playerView.player = exoPlayer

    }


    @OptIn(UnstableApi::class)
    fun prepare(mediaString: String, context: Context) {
        exoPlayer?.apply {
            val mediaSource = buildMediaSource(mediaString.toUri(), context)
            setMediaSource(mediaSource)
            addMediaSource(mediaSource)
            seekTo(START_PLAYBACK_POSITION)
            repeatMode =  REPEAT_MODE_OFF
            playWhenReady = true
            prepare()
        }
    }

    @OptIn(UnstableApi::class)
    private fun buildMediaSource(uri: Uri, context: Context): MediaSource {
        val mediaItem = MediaItem.fromUri(uri)
        return ProgressiveMediaSource.Factory(DefaultDataSource.Factory(context))
            .createMediaSource(mediaItem)
    }


    fun stop() {
        exoPlayer?.stop()
        releaseResources()

    }

    private fun releaseResources() {
        exoPlayer?.release()
        exoPlayer = null
    }
}