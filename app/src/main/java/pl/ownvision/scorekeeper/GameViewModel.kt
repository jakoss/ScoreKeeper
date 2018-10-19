package pl.ownvision.scorekeeper

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxViewModelFactory
import org.koin.android.ext.android.inject
import pl.ownvision.scorekeeper.core.MvRxViewModel
import pl.ownvision.scorekeeper.core.ScreenEnum
import pl.ownvision.scorekeeper.db.AppDatabase
import pl.ownvision.scorekeeper.db.entities.Move
import pl.ownvision.scorekeeper.db.entities.Player
import pl.ownvision.scorekeeper.exceptions.ValidationException

class GameViewModel(
        initialState: GameState,
        private val database: AppDatabase,
        private val context: Context
) : MvRxViewModel<GameState>(initialState) {

    init {
        logStateChanges()
        database.movesDao()
                .getScores()
                .execute { copy(scores = it) }
        database.movesDao()
                .getMoves()
                .execute { copy(moves = it) }
        database.statsDao()
                .getTimeline()
                .execute { copy(timeline = it) }
    }

    fun changeScreen(screen: ScreenEnum) = setState { copy(screen = screen) }

    fun addPlayer(name: String) {
        runActionInBackground {
            validatePlayerName(name)
            val player = Player(name)
            val playerId = database.playersDao().insert(player)
            val move = Move(score = 0, playerId = playerId, playerName = "")
            database.movesDao().insert(move)
        }
    }

    fun addMove(playerId: Long, points: Int) {
        runActionInBackground {
            validateMoveScore(points)
            val move = Move(playerId = playerId, score = points, playerName = "")
            database.movesDao().insert(move)
        }
    }

    fun removeMove(move: Move) {
        runActionInBackground {
            database.movesDao().delete(move)
        }
    }

    fun resetScore() {
        runActionInBackground {
            database.movesDao().resetMoves()
        }
    }

    fun resetGame() {
        runActionInBackground {
            database.clearAllTables()
        }
    }

    private fun validatePlayerName(name: String){
        if(name.isEmpty()) throw ValidationException(context.getString(R.string.validation_name_cannot_be_empty))
        if(playerExists(name)) throw ValidationException(context.getString(R.string.validation_name_already_taken))
    }

    private fun validateMoveScore(points: Int){
        if(points == 0) throw ValidationException(context.getString(R.string.validation_move_zero))
    }

    private fun playerExists(name: String) = database.playersDao().getPlayersWithName(name.trim()) == 1

    companion object : MvRxViewModelFactory<GameState> {
        @JvmStatic override fun create(activity: FragmentActivity, state: GameState): BaseMvRxViewModel<GameState> {
            val database by activity.inject<AppDatabase>()
            val context by activity.inject<Context>()
            return GameViewModel(state, database, context)
        }
    }
}