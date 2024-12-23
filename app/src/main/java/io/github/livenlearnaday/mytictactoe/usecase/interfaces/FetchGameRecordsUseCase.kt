package io.github.livenlearnaday.mytictactoe.usecase.interfaces

import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import kotlinx.coroutines.flow.Flow

fun interface FetchGameRecordsUseCase{
    fun execute(): Flow<List<GameRecord>>
}