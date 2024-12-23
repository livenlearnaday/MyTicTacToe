package io.github.livenlearnaday.mytictactoe.history

import io.github.livenlearnaday.mytictactoe.data.model.GameRecord

data class HistoryState(
    var gameRecords: List<GameRecord> = emptyList(),
    val isRecordsUpdated: Boolean = false
)
