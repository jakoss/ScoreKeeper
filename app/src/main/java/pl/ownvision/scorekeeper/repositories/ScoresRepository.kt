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

class ScoresRepository(
        realm: Realm, context: Context
): BaseRepository(realm, context) {

    lateinit var gameId: String
    private lateinit var game: Game

    fun getScoresList(): List<Score> {
        setGame()
        val scores = mutableListOf<Score>()
        game.players.forEach {
            val playerMoves = game.moves.where().equalTo("player.id", it.id).findAll()
            val points = playerMoves.sumBy { it.score }

            scores.add(Score(it, points, playerMoves.count()))
        }
        return scores
    }

    fun createMove(player: Player, points: Int) {
        val move = Move()
        move.player = player
        move.score = points
        validateMove(move)
        setGame()
        realm.executeTransaction {
            game.moves.add(move)
        }
    }

    fun removeMove(move: Move){
        realm.executeTransaction {
            val result = it.where(Move::class.java)
                    .equalTo("id", move.id)
                    .findFirst()
            result.deleteFromRealm()
        }
    }

    private fun validateMove(move: Move){
        if(move.player == null) throw ValidationException("Player is null")

    }

    private fun setGame(): Game {
        game = realm.where(Game::class.java)
                .equalTo("id", gameId)
                .findFirst()
        return game
    }
}

        */