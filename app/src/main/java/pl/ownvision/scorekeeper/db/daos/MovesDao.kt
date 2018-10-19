package pl.ownvision.scorekeeper.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable
import pl.ownvision.scorekeeper.db.entities.Move
import pl.ownvision.scorekeeper.db.entities.Score

@Dao
interface MovesDao {
    @Insert
    fun insert(move: Move): Long

    @Delete
    fun delete(move: Move)

    @Query("select count(*) from moves")
    fun getMoveCount(): Int

    @Query("select m.id, m.playerId, p.name as playerName, m.score, m.createdAt from moves as m join players as p on p.id = m.playerId where m.score != 0 order by m.createdAt desc")
    fun getMoves(): Observable<List<Move>>

    @Query("""
    select m.playerId as playerId, p.name as playerName, sum(m.score) as points, count(m.id) - 1 as moveCount
    from moves as m
    join players as p on p.id = m.playerId
    group by m.playerId, p.name
    order by points desc
    """)
    fun getScores(): Observable<List<Score>>

    @Query("delete from moves where score != 0")
    fun resetMoves()
}