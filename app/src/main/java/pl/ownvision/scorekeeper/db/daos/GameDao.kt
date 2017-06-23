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

    @Query("select * from games where id = :p0")
    fun get(p0: Long): Game

    @Query("select * from games order by createdAt desc")
    fun getAll(): LiveData<List<Game>>

    @Query("update games set name = :p1 where id = :p0")
    fun setName(p0: Long, p1: String) // TODO : revert param names after kotlin fix https://youtrack.jetbrains.com/issue/KT-17959
}