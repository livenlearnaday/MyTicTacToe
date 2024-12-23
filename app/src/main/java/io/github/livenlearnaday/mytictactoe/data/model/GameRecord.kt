package io.github.livenlearnaday.mytictactoe.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_records")
data class GameRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fileName: String = "",
    val filePath: String = "",
    val winner: String = "",
    val isFileDeleted: Boolean = false
)


