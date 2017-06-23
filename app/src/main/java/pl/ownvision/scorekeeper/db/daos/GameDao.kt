package pl.ownvision.scorekeeper.db.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import pl.ownvision.scorekeeper.db.entities.Game

/**
 * Created by jakub on 23.06.2017.
 */

@Dao
interface GameDao {
    @Insert
    fun insert(game: Game): Long

    @Delete
    fun delete(game: Game)

    @Query("select * from games where id = :id")
    fun get(id: Long): Game

    @Query("select * from games")
    fun getAll(): LiveData<List<Game>>

    @Query("update games set name = :name where id = :id")
    fun setName(id: Long, name: String)
}