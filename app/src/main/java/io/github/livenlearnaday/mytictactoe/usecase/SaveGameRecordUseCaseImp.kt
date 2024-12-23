package io.github.livenlearnaday.mytictactoe.usecase

import io.github.livenlearnaday.mytictactoe.data.local.GameRecordDao
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import io.github.livenlearnaday.mytictactoe.data.repository.GameRepository
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.SaveGameRecordUseCase

class SaveGameRecordUseCaseImp(
    private val gameRepository: GameRepository
): SaveGameRecordUseCase {
    override suspend fun execute(gameRecord: GameRecord){
        gameRepository.insertGameRecord(gameRecord)
    }
}