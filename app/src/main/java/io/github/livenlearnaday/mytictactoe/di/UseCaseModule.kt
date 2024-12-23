package io.github.livenlearnaday.mytictactoe.di

import io.github.livenlearnaday.mytictactoe.data.repository.GameRepository
import io.github.livenlearnaday.mytictactoe.usecase.DeleteGameRecordByIdUseCaseImp
import io.github.livenlearnaday.mytictactoe.usecase.FetchGameRecordsUseCaseImp
import io.github.livenlearnaday.mytictactoe.usecase.SaveGameRecordUseCaseImp
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.DeleteGameRecordByIdUseCase
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.FetchGameRecordsUseCase
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.SaveGameRecordUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory<FetchGameRecordsUseCase> {
        FetchGameRecordsUseCaseImp(get<GameRepository>())
    }

    factory<SaveGameRecordUseCase> {
        SaveGameRecordUseCaseImp(get<GameRepository>())
    }

    factory<DeleteGameRecordByIdUseCase> {
        DeleteGameRecordByIdUseCaseImp(get<GameRepository>())
    }

}