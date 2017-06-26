package pl.ownvision.scorekeeper.viewmodels

import android.arch.lifecycle.ViewModel
import android.content.Context
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.db.daos.*
import pl.ownvision.scorekeeper.db.entities.*
import pl.ownvision.scorekeeper.exceptions.ValidationException
import javax.inject.Inject

/**
 * Created by Jakub on 26.06.2017.
 */

class GameViewModel
@Inject constructor(
        private val gameDao: GameDao,
        private val movesDao: MovesDao,
        private val playersDao: PlayersDao,
        private val context: Context
): ViewModel() {
    private var gameId: Long = 0

    fun init(gameId: Long) {
        this.gameId = gameId
    }

    fun getGameTitle() = gameDao.get(gameId).name

    fun getScores() = gameDao.getScores(gameId)

    fun addMove(playerId: Long, points: Int) {
        val move = Move()
        move.gameId = gameId
        move.playerId = playerId
        move.score = points
        movesDao.insert(move)
    }

    fun deleteMove(move: Move) = movesDao.delete(move)

    fun getMoves() = movesDao.getMoves(gameId)

    fun addPlayer(name: String) {
        validatePlayerName(name)
        val player = Player()
        player.gameId = gameId
        player.name = name.trim()
        val playerId = playersDao.insert(player)
        val move = Move()
        move.gameId = gameId
        move.playerId = playerId
        move.score = 0
        movesDao.insert(move)
    }

    fun renamePlayer(player: Player, newName: String) {
        validatePlayerName(newName)
        playersDao.renamePlayer(player.id, newName.trim())
    }

    fun removePlayer(player: Player) = playersDao.delete(player)

    fun getPlayers() = playersDao.getAll(gameId)

    fun canEditPlayers() = movesDao.getMoveCount(gameId) == 0

    private fun validatePlayerName(name: String){
        if(name.isNullOrEmpty()) throw ValidationException(context.getString(R.string.validation_name_cannot_be_empty))
        if(playerExists(name)) throw ValidationException(context.getString(R.string.validation_name_already_taken))
    }

    private fun playerExists(name: String) = playersDao.getPlayersWithName(gameId, name.trim()) == 1
}