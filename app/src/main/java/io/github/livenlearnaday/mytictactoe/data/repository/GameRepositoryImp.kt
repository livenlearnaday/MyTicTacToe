package io.github.livenlearnaday.mytictactoe.data.repository

import io.github.livenlearnaday.mytictactoe.data.local.GameLocalDatasource
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import kotlinx.coroutines.flow.Flow

class GameRepositoryImp(
    private val gameLocalDatasource: GameLocalDatasource
): GameRepository {
    override suspend fun insertGameRecord(gameRecord: GameRecord) {
        gameLocalDatasource.insertGameRecord(gameRecord)
    }

    override fun getGameRecords(): Flow<List<GameRecord>> {
        return gameLocalDatasource.getGameRecords()
    }

    override suspend fun deleteGameRecordById(id: Int) {
        gameLocalDatasource.deleteGameRecordById(id)
    }

    override suspend fun deleteAllGameRecords() {
        gameLocalDatasource.deleteAllGameRecords()
    }


}