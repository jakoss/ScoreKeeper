package pl.ownvision.scorekeeper.db.daos

import androidx.room.Dao
import androidx.room.Query
import pl.ownvision.scorekeeper.db.entities.Move

@Dao
interface StatsDao {
    @Query("select m.id, m.playerId, p.name as playerName, m.score, m.gameId, m.createdAt from moves as m join players as p on p.id = m.playerId where m.gameId = :gameId")
    fun getTimeline(gameId: Long): List<Move>
}