package io.github.livenlearnaday.mytictactoe.data.local

import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import kotlinx.coroutines.flow.Flow

class GameLocalDatasourceImp(
    private val gameRecordDao: GameRecordDao
): GameLocalDatasource {
    override suspend fun insertGameRecord(gameRecord: GameRecord) {
        gameRecordDao.insertGameRecord(gameRecord)
    }

    override fun getGameRecords(): Flow<List<GameRecord>> {
        return gameRecordDao.getGameRecords()
    }

    override suspend fun deleteGameRecordById(id: Int) {
       gameRecordDao.deleteGameRecordById(id)
    }

    override suspend fun deleteAllGameRecords() {
        gameRecordDao.deleteAllGameRecords()
    }

}