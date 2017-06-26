package pl.ownvision.scorekeeper.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import pl.ownvision.scorekeeper.db.converters.*
import pl.ownvision.scorekeeper.db.daos.*
import pl.ownvision.scorekeeper.db.entities.*

/**
 * Created by jakub on 23.06.2017.
 */

@Database(entities = arrayOf(Game::class, Move::class, Player::class), version = 1)
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        val DATABASE_NAME = "score-keeper-db"
    }

    abstract fun gameDao(): GameDao
    abstract fun movesDao(): MovesDao
    abstract fun playersDao(): PlayersDao
}