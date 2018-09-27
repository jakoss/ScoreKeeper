package pl.ownvision.scorekeeper.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.ownvision.scorekeeper.db.entities.Move
import pl.ownvision.scorekeeper.db.entities.Player

@Dao
interface PlayersDao {
    @Insert
    fun insert(player: Player): Long

    @Delete
    fun delete(player: Player)

    @Query("select * from players where gameId = :gameId")
    fun getAll(gameId: Long): LiveData<List<Player>>

    @Query("update players set name = :name where id = :id")
    fun renamePlayer(id: Long, name: String)

    @Query("select count(*) from players where gameId = :gameId and name = :name")
    fun getPlayersWithName(gameId: Long, name: String): Int

    @Query("select count(*) from players where gameId = :gameId")
    fun getPlayerCount(gameId: Long): Int
}