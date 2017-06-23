package pl.ownvision.scorekeeper.repositories

/*

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

class PlayerRepository(
        realm: Realm, context: Context
): BaseRepository(realm, context) {

    lateinit var gameId: String
    private lateinit var game: Game

    fun createNewPlayer(name: String): Player {
        val player = Player()
        player.name = name
        validatePlayer(player)
        realm.executeTransaction {
            val game = setGame()
            game.players.add(player)
        }
        return player
    }
    fun updatePlayer(player: Player) {
        validatePlayer(player)
        realm.executeTransaction {
            it.copyToRealmOrUpdate(player)
        }
    }

    fun removePlayer(id: String) {
        realm.executeTransaction {
            val game = setGame()
            val player = game.players.where()
                    .equalTo("id", id)
                    .findFirst()
            game.players.remove(player)
        }
    }

    fun getPlayers(): List<Player> {
        setGame()
        val results = game.players
        return realm.copyFromRealm(results)
    }

    fun canUpdate(): Boolean {
        setGame()
        return game.moves.count() == 0
    }

    private fun validatePlayer(player: Player){
        if(player.name.isBlank()){
            throw ValidationException(context.getString(R.string.validation_name_cannot_be_empty))
        }
        if(nameExists(player.name)){
            throw ValidationException(context.getString(R.string.validation_name_already_taken))
        }
    }

    private fun nameExists(name: String): Boolean{
        val player = game.players.where().equalTo("name", name).findFirst()
        return player != null
    }

    private fun setGame(): Game {
        game = realm.where(Game::class.java)
                .equalTo("id", gameId)
                .findFirst()
        return game
    }
}

        */