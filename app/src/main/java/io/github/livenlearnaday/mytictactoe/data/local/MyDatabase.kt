package io.github.livenlearnaday.mytictactoe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord

@Database(
    entities = [
        GameRecord::class
    ],
    version = 1,
    exportSchema = false
)

abstract class MyDatabase : RoomDatabase() {
    abstract fun gameRecordDao(): GameRecordDao
}