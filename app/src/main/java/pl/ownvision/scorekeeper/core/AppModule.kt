package pl.ownvision.scorekeeper.core

import android.content.Context
import dagger.Module
import dagger.Provides
import io.realm.Realm
import pl.ownvision.scorekeeper.repositories.*
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
    fun provideRealm(): Realm = Realm.getDefaultInstance()

    @Provides
    fun provideGameRepository(realm: Realm, context: Context): GameRepository = GameRepository(realm, context)

    @Provides
    fun providePlayerRepository(realm: Realm, context: Context): PlayerRepository = PlayerRepository(realm, context)

    @Provides
    fun provideScoresRepository(realm: Realm, context: Context): ScoresRepository = ScoresRepository(realm, context)
}