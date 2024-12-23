package io.github.livenlearnaday.mytictactoe.di

import io.github.livenlearnaday.mytictactoe.game.GameViewModel
import io.github.livenlearnaday.mytictactoe.history.HistoryViewModel
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.DeleteGameRecordByIdUseCase
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.FetchGameRecordsUseCase
import io.github.livenlearnaday.mytictactoe.usecase.interfaces.SaveGameRecordUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel<GameViewModel> {
        GameViewModel(
            get<SaveGameRecordUseCase>()
        )
    }

    viewModel<HistoryViewModel> {
        HistoryViewModel(
            get<FetchGameRecordsUseCase>(),
            get<DeleteGameRecordByIdUseCase>()
        )
    }

}

