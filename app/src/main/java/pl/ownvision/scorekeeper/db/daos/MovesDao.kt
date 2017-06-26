package pl.ownvision.scorekeeper.db.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import pl.ownvision.scorekeeper.db.entities.Move

/**
 * Created by Jakub on 26.06.2017.
 */

@Dao
interface MovesDao {
    @Insert
    fun insert(move: Move): Long

    @Delete
    fun delete(move: Move)

    @Query("select count(*) from moves where gameId = :gameId")
    fun getMoveCount(gameId: Long): Int

    @Query("select m.id, m.playerId, p.name as playerName, m.score, m.gameId from moves as m join players as p on p.id = m.playerId where m.gameId = :gameId and m.score != 0 order by m.createdAt desc")
    fun getMoves(gameId: Long): LiveData<List<Move>>
}