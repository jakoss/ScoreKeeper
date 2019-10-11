package pl.ownvision.scorekeeper.core

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pl.ownvision.scorekeeper.db.AppDatabase

val applicationModule = module {
    single {
        val appDatabase = Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.DATABASE_NAME).build()
        appDatabase
    }
}