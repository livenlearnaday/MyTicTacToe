package io.github.livenlearnaday.mytictactoe.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface GameRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameRecord(gameRecord: GameRecord)

    @Query("SELECT * FROM game_records")
    fun getGameRecords(): Flow<List<GameRecord>>

    @Query("DELETE FROM game_records WHERE id=:id")
    suspend fun deleteGameRecordById(id: Int)

    @Query("DELETE FROM game_records")
    suspend fun deleteAllGameRecords()


}