package pl.ownvision.scorekeeper.viewmodels

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.github.mikephil.charting.data.*
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
        private val statsDao: StatsDao,
        private val context: Context
): ViewModel() {
    private var gameId: Long = 0

    fun init(gameId: Long) {
        this.gameId = gameId
    }

    fun getGameTitle() = gameDao.get(gameId).name

    fun getScores() = gameDao.getScores(gameId)

    fun addMove(playerId: Long, points: Int) {
        validateMoveScore(points)
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

    fun canEditPlayers() = movesDao.getMoveCount(gameId) == playersDao.getPlayerCount(gameId)

    fun getTimeline() : LineData {
        val moves = statsDao.getTimeline(gameId).groupBy { it.playerName }
        val colors = context.resources.getIntArray(R.array.rainbow)
        val lineDataSets = mutableListOf<LineDataSet>()
        var colorCounter = 0
        moves.forEach {
            val entries = mutableListOf<Entry>()
            var counter = 1
            var sum = 0
            val sortedMoves = it.value.sortedBy { it.createdAt }
            sortedMoves.forEach {
                sum += it.score
                entries.add(Entry(counter.toFloat(), sum.toFloat()))
                counter++
            }
            val lineDataSet = LineDataSet(entries, it.key)
            val color = colors[colorCounter.rem(colors.size)]
            lineDataSet.color = color
            lineDataSet.valueTextColor = color
            lineDataSets.add(lineDataSet)
            colorCounter++
        }
        return LineData(lineDataSets.toList())
    }

    private fun validatePlayerName(name: String){
        if(name.isEmpty()) throw ValidationException(context.getString(R.string.validation_name_cannot_be_empty))
        if(playerExists(name)) throw ValidationException(context.getString(R.string.validation_name_already_taken))
    }

    private fun validateMoveScore(points: Int){
        if(points == 0) throw ValidationException(context.getString(R.string.validation_move_zero))
    }

    private fun playerExists(name: String) = playersDao.getPlayersWithName(gameId, name.trim()) == 1
}