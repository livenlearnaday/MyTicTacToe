package io.github.livenlearnaday.mytictactoe.navigation

import GameScreen
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.github.livenlearnaday.mytictactoe.game.GameViewModel
import org.koin.androidx.compose.koinViewModel

data object GameScreenRoute: Screen {

    @Composable
    override fun Content() {
        val gameViewModel = koinViewModel<GameViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val gameState = gameViewModel.gameState

        GameScreen(
            gameViewModel = gameViewModel,
            gameState = gameState,
            onHistoryButtonClicked = {
                navigator.push(HistoryScreenRoute)
            },
            onGameAction = gameViewModel::gameAction
        )
    }




}