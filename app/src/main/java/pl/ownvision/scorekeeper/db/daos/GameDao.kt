package pl.ownvision.scorekeeper.db.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import pl.ownvision.scorekeeper.db.entities.Game
import pl.ownvision.scorekeeper.db.entities.Score

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

    @Query("select * from games order by createdAt desc")
    fun getAll(): LiveData<List<Game>>

    @Query("update games set name = :name where id = :id")
    fun setName(id: Long, name: String)

    @Query("""
    select m.playerId as playerId, p.name as playerName, sum(m.score) as points, count(m.id) - 1 as moveCount
    from moves as m
    join players as p on p.id = m.playerId
    where m.gameId = :gameId
    group by m.playerId, p.name
    order by points desc
    """)
    fun getScores(gameId: Long): LiveData<List<Score>>
}