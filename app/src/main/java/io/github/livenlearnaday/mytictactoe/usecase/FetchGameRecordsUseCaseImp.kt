package io.github.livenlearnaday.mytictactoe.usecase

import io.github.livenlearnaday.mytictactoe.data.local.GameRecordDao
import io.github.livenlearnaday.mytictactoe.data.model.GameRecord
import io.github.livenlearnaday.mytictactoe.data.repository.GameRepository
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.FetchGameRecordsUseCase
import kotlinx.coroutines.flow.Flow

class FetchGameRecordsUseCaseImp(
    private val gameRepository: GameRepository
): FetchGameRecordsUseCase {
    override fun execute(): Flow<List<GameRecord>> {
       return gameRepository.getGameRecords()
    }
}