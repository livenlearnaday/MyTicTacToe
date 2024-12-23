package io.github.livenlearnaday.mytictactoe.game

import io.github.livenlearnaday.mytictactoe.data.enums.Player
import io.github.livenlearnaday.mytictactoe.data.enums.UiMode
import io.github.livenlearnaday.mytictactoe.data.model.Winner
import io.github.livenlearnaday.mytictactoe.game.GameViewModel.Companion.DEFAULT_GRID_SIZE

data class GameState (
    val winner: Winner = Winner(),
    val currentPlayer: Player = Player.X,
    val isSinglePlayer: Boolean = false,
    val isMoved: Boolean = false,
    val uiMode: UiMode = UiMode.NO_ACTION,
    val gridSize: Int = DEFAULT_GRID_SIZE,
    val isShowDialog: Boolean = false,
    val moveCount: Int = 0
)
