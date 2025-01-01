package io.github.livenlearnaday.mytictactoe.history

import io.github.livenlearnaday.mytictactoe.data.model.GameRecord


sealed interface HistoryListAction {
    data class OnDeleteById(val gameRecord: GameRecord): HistoryListAction
    data object OnRecordsUpdated: HistoryListAction
    data object OnScreenLaunched: HistoryListAction
}