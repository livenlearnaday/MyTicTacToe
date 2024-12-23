package io.github.livenlearnaday.mytictactoe.di

import io.github.livenlearnaday.mytictactoe.data.local.GameLocalDatasource
import io.github.livenlearnaday.mytictactoe.data.local.GameLocalDatasourceImp
import io.github.livenlearnaday.mytictactoe.data.local.GameRecordDao
import io.github.livenlearnaday.mytictactoe.data.repository.GameRepository
import io.github.livenlearnaday.mytictactoe.data.repository.GameRepositoryImp
import org.koin.dsl.module


val datasourceModule = module {

    factory<GameRepository> {
        GameRepositoryImp(
            get<GameLocalDatasource>()
        )
    }

    factory<GameLocalDatasource> {
        GameLocalDatasourceImp(get<GameRecordDao>())
    }


}