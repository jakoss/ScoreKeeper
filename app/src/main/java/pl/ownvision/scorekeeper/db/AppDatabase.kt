package pl.ownvision.scorekeeper.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.ownvision.scorekeeper.db.converters.*
import pl.ownvision.scorekeeper.db.daos.*
import pl.ownvision.scorekeeper.db.entities.*

@Database(entities = arrayOf(
        Game::class,
        Move::class,
        Player::class
), version = 1)
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        val DATABASE_NAME = "score-keeper-db"
    }

    abstract fun gameDao(): GameDao
    abstract fun movesDao(): MovesDao
    abstract fun playersDao(): PlayersDao
    abstract fun statsDao(): StatsDao
}