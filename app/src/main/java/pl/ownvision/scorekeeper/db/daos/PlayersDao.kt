package pl.ownvision.scorekeeper.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable
import pl.ownvision.scorekeeper.db.entities.Player

@Dao
interface PlayersDao {
    @Insert
    fun insert(player: Player): Long

    @Delete
    fun delete(player: Player)

    @Query("select * from players")
    fun getAll(): Observable<List<Player>>

    @Query("update players set name = :name where id = :id")
    fun renamePlayer(id: Long, name: String)

    @Query("select count(*) from players where name = :name")
    fun getPlayersWithName(name: String): Int

    @Query("select count(*) from players")
    fun getPlayerCount(): Int
}