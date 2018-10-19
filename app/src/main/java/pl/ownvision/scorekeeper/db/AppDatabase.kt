package pl.ownvision.scorekeeper.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.ownvision.scorekeeper.db.converters.*
import pl.ownvision.scorekeeper.db.daos.*
import pl.ownvision.scorekeeper.db.entities.*

@Database(entities = [Move::class, Player::class], version = 1, exportSchema = false)
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "score-keeper-db-v2"
    }

    abstract fun movesDao(): MovesDao
    abstract fun playersDao(): PlayersDao
    abstract fun statsDao(): StatsDao
}