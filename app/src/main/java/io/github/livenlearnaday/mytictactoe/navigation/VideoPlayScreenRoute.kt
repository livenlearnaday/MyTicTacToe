package io.github.livenlearnaday.mytictactoe.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import io.github.livenlearnaday.mytictactoe.history.HistoryItemVideoPlayScreen
import io.github.livenlearnaday.mytictactoe.history.HistoryViewModel
import org.koin.androidx.compose.koinViewModel

data class VideoPlayScreenRoute( val gameRecord: GameRecord): Screen {

    @Composable
    override fun Content() {
        val historyViewModel = koinViewModel<HistoryViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val historyState = historyViewModel.historyState


        HistoryItemVideoPlayScreen(
            filePath = gameRecord.filePath
        )
    }




}