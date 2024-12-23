package io.github.livenlearnaday.mytictactoe

import android.app.Application
import io.github.livenlearnaday.mytictactoe.di.localCacheModule
import io.github.livenlearnaday.mytictactoe.di.datasourceModule
import io.github.livenlearnaday.mytictactoe.di.useCaseModule
import io.github.livenlearnaday.mytictactoe.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                viewModelModule,
                localCacheModule,
                datasourceModule,
                useCaseModule
            )
        }
    }
}