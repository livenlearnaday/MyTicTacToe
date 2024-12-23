package io.github.livenlearnaday.mytictactoe.usecase.interfaces

fun interface DeleteGameRecordByIdUseCase {
    suspend fun execute(id: Int)
}