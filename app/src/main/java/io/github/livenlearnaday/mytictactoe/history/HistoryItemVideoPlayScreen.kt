package io.github.livenlearnaday.mytictactoe.history

import MyTicTacToeTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import io.github.livenlearnaday.mytictactoe.media.MyVideoPlayerManager
import io.github.livenlearnaday.mytictactoe.utils.getVideoFilePathFromFileName

@Composable
fun HistoryItemVideoPlayScreen(
gameRecord: GameRecord
){

    DisposableEffect(Unit) {
        onDispose {
            MyVideoPlayerManager.stop()
        }
    }

    // Adds view to Compose
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            PlayerView(context).apply {
                MyVideoPlayerManager.initializePlayer(this, context)
                MyVideoPlayerManager.prepare(gameRecord.fileName.getVideoFilePathFromFileName, context)
            }
        }
    )


}


@Preview
@Composable
fun HistoryItemVideoPlayScreenPreview() {
    MyTicTacToeTheme {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PlayerView(context).apply {
                    MyVideoPlayerManager.initializePlayer(this, context)
                    MyVideoPlayerManager.prepare("https://youtu.be/nk2h37Jj7UA?si=H_b9ml2dN0OxR7ry", this.context)
                }
            }
        )
    }
}