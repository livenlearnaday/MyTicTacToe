package io.github.livenlearnaday.mytictactoe.usecase

import io.github.livenlearnaday.mytictactoe.data.repository.GameRepository
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.DeleteGameRecordByIdUseCase

class DeleteGameRecordByIdUseCaseImp(
    private val gameRepository: GameRepository
): DeleteGameRecordByIdUseCase {
    override suspend fun execute(id: Int){
        gameRepository.deleteGameRecordById(id)
    }
}