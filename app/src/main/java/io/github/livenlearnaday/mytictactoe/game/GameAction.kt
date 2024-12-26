package io.github.livenlearnaday.mytictactoe.game

import io.github.livenlearnaday.mytictactoe.data.enums.UiMode
import io.github.livenlearnaday.mytictactoe.data.model.Cell

sealed interface GameAction {
    data object ResetGame : GameAction
    data object ResetAll : GameAction
    data class DialogDismiss(val uiMode: UiMode) : GameAction
    data object DialogClose : GameAction
    data object OnPlayerMove : GameAction
    data class OnGridSizeChange(val gridSizeString: String) : GameAction
    data object OnClickResetIcon : GameAction
    data object OnClickHistoryPlaysIcon : GameAction
    data object OnClickMultiPlayerMode : GameAction
    data object OnClickSinglePlayerMode : GameAction
    data class OnClickCell(val cell: Cell) : GameAction
    data object StopRecording : GameAction


}