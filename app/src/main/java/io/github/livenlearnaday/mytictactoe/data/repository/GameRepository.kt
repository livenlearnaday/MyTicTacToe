package io.github.livenlearnaday.mytictactoe.data.repository

import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun insertGameRecord(gameRecord: GameRecord)
    fun getGameRecords(): Flow<List<GameRecord>>
    suspend fun deleteGameRecordById(id: Int)
    suspend fun deleteAllGameRecords()
}