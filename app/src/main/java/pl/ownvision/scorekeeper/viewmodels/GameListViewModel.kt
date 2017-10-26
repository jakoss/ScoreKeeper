package pl.ownvision.scorekeeper.viewmodels

import android.arch.lifecycle.ViewModel
import android.content.Context
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.db.daos.GameDao
import pl.ownvision.scorekeeper.db.entities.Game
import pl.ownvision.scorekeeper.exceptions.ValidationException
import javax.inject.Inject

/**
 * Created by Jakub on 26.06.2017.
 */

class GameListViewModel
    @Inject constructor(
            private val gameDao: GameDao,
            private val context: Context
    ) : ViewModel() {

    fun getAllGames() = gameDao.getAll()

    fun addGame(input: String) {
        if (input.isEmpty()) throw ValidationException(context.getString(R.string.validation_name_cannot_be_empty))
        val game = Game()
        game.name = input
        gameDao.insert(game)
    }

    fun removeGame(game: Game){
        gameDao.delete(game)
    }

    fun renameGame(game: Game, newName: String){
        if (newName.isEmpty()) throw ValidationException(context.getString(R.string.validation_name_cannot_be_empty))
        gameDao.setName(game.id, newName)
    }
}