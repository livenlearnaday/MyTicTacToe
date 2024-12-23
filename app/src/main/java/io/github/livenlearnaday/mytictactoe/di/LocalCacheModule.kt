package io.github.livenlearnaday.mytictactoe.di

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import io.github.livenlearnaday.mytictactoe.data.local.GameRecordDao
import io.github.livenlearnaday.mytictactoe.data.local.MyDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


fun provideDataBase(application: Application): MyDatabase =
     Room.databaseBuilder(
        application,
         MyDatabase::class.java,
        "mytictactoe"
    ).
     fallbackToDestructiveMigration().build()

fun provideDao(database: MyDatabase): GameRecordDao = database.gameRecordDao()


val localCacheModule = module {

    single { provideDataBase(get()) }
    single { provideDao(get()) }

    single<SharedPreferences> {
        EncryptedSharedPreferences(
            androidApplication(),
            "secret_shared_prefs",
            MasterKey(androidApplication())
        )
    }





}


