package pl.ownvision.scorekeeper.core

import android.content.Context
import dagger.Module
import dagger.Provides
import io.realm.Realm
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
}