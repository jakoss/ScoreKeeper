package pl.ownvision.scorekeeper.core

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import pl.ownvision.scorekeeper.db.AppDatabase
import javax.inject.Singleton

/**
 * Created by jakub on 30.05.2017.
 */

@Module
class AppModule(val app: App){

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApp(): App = app

    @Provides
    @Singleton
    fun provideDatabase(app: App): AppDatabase = Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME).build()
}