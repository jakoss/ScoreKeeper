package pl.ownvision.scorekeeper.repositories

import android.content.Context
import io.realm.Case
import io.realm.Realm
import io.realm.Sort
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.exceptions.*
import pl.ownvision.scorekeeper.models.*

/**
 * Created by jakub on 01.06.2017.
 */

class GameRepository(
        realm: Realm, context: Context
): BaseRepository(realm, context) {
    fun getAllGames(): List<Game> {
        val results = realm.where(Game::class.java)
                .findAllSorted("createdAt", Sort.DESCENDING)
        return realm.copyFromRealm(results)
    }

    fun getGame(id: String): Game {
        val game = realm.where(Game::class.java)
                .equalTo("id", id)
                .findFirst()
        return realm.copyFromRealm(game)
    }

    fun createGame(name: String): Game {
        val game = Game()
        game.name = name
        validateGame(game)
        realm.executeTransaction {
            it.copyToRealm(game)
        }
        return game
    }

    fun updateGame(game: Game){
        validateGame(game)
        realm.executeTransaction {
            it.copyToRealmOrUpdate(game)
        }
    }

    fun removeGame(game: Game){
        realm.executeTransaction {
            val result = it.where(Game::class.java)
                    .equalTo("id", game.id)
                    .findFirst()
            result.deleteFromRealm()
        }
    }

    fun nameExists(name: String): Boolean {
        val count = realm.where(Game::class.java)
                .equalTo("name", name, Case.INSENSITIVE)
                .count()
        return count > 0
    }

    private fun validateGame(game: Game){
        if(game.name.isBlank()){
            throw ValidationException(context.getString(R.string.validation_name_cannot_be_empty))
        }
        if(nameExists(game.name)){
            throw ValidationException(context.getString(R.string.validation_name_already_taken))
        }
    }
}