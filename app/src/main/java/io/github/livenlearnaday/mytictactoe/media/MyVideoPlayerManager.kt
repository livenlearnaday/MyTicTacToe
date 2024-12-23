package io.github.livenlearnaday.mytictactoe.media

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

data object MyVideoPlayerManager {
    private var exoPlayer: ExoPlayer? = null

        private const val START_MEDIA_INDEX = 0
        private const val START_PLAYBACK_POSITION = 0L
        private const val VOLUME = 0f


    fun initializePlayer(playerView: PlayerView, context: Context) {
        exoPlayer = ExoPlayer
            .Builder(context)
            .build()
        playerView.player = exoPlayer

    }

    fun prepare(media: String) {
        val mediaItem = MediaItem.fromUri(media)
        exoPlayer?.apply {
            volume = VOLUME
            setMediaItems(listOf(mediaItem), START_MEDIA_INDEX, START_PLAYBACK_POSITION)
            pause()
            prepare()
        }
    }

    fun restart() {
        exoPlayer?.apply {
            seekTo(START_PLAYBACK_POSITION)
            play()
        }
    }

    fun pause() {
        exoPlayer?.apply {
            pause()
        }
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