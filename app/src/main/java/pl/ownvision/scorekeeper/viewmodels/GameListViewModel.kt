package pl.ownvision.scorekeeper.viewmodels

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.*
import org.koin.android.ext.android.inject
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.MvRxViewModel
import pl.ownvision.scorekeeper.db.daos.GameDao
import pl.ownvision.scorekeeper.db.entities.Game
import pl.ownvision.scorekeeper.exceptions.ValidationException

data class GameListViewModelState(
        val games: List<Game> = emptyList(),
        val databaseRequest: Async<List<Game>> = Uninitialized
) : MvRxState

class GameListViewModel(
        initialState: GameListViewModelState,
        private val gameDao: GameDao,
        private val context: Context
    ) : MvRxViewModel<GameListViewModelState>(initialState) {

    init {
        logStateChanges()
        getAllGames()
    }

    fun getAllGames() {
        gameDao.getAll()
                .execute { copy(games = it() ?: games, databaseRequest = it)}
    }

    fun addGame(input: String) {
        if (input.isEmpty()) throw ValidationException(context.getString(R.string.validation_name_cannot_be_empty))
        runActionInBackground {
            val game = Game(name = input)
            gameDao.insert(game)
        }
    }

    fun removeGame(game: Game){
        runActionInBackground {
            gameDao.delete(game)
        }
    }

    fun renameGame(game: Game, newName: String){
        if (newName.isEmpty()) throw ValidationException(context.getString(R.string.validation_name_cannot_be_empty))
        runActionInBackground {
            gameDao.setName(game.id, newName)
        }
    }

    companion object : MvRxViewModelFactory<GameListViewModelState> {
        @JvmStatic override fun create(activity: FragmentActivity, state: GameListViewModelState): BaseMvRxViewModel<GameListViewModelState> {
            val gameDao by activity.inject<GameDao>()
            val context by activity.inject<Context>()
            return GameListViewModel(state, gameDao, context)
        }
    }
}