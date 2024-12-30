package io.github.livenlearnaday.mytictactoe.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import io.github.livenlearnaday.mytictactoe.history.HistoryItemVideoPlayScreen

data class VideoPlayScreenRoute( val gameRecord: GameRecord): Screen {

    @Composable
    override fun Content() {
        HistoryItemVideoPlayScreen(
            gameRecord = gameRecord
        )
    }




}