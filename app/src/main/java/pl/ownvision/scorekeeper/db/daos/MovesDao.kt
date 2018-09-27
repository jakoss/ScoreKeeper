package pl.ownvision.scorekeeper.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.ownvision.scorekeeper.db.entities.Move

@Dao
interface MovesDao {
    @Insert
    fun insert(move: Move): Long

    @Delete
    fun delete(move: Move)

    @Query("select count(*) from moves where gameId = :gameId")
    fun getMoveCount(gameId: Long): Int

    @Query("select m.id, m.playerId, p.name as playerName, m.score, m.gameId, m.createdAt from moves as m join players as p on p.id = m.playerId where m.gameId = :gameId and m.score != 0 order by m.createdAt desc")
    fun getMoves(gameId: Long): LiveData<List<Move>>
}