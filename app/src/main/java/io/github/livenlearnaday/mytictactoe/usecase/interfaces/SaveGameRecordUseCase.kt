package io.github.livenlearnaday.mytictactoe.usecase.interfaces

import io.github.livenlearnaday.mytictactoe.data.model.GameRecord

fun interface SaveGameRecordUseCase {
    suspend fun execute(gameRecord: GameRecord)
}