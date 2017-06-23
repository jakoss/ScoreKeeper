package pl.ownvision.scorekeeper.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE

/**
 * Created by jakub on 31.05.2017.
 */

@Entity(tableName = "players",
        foreignKeys = arrayOf(
                ForeignKey(entity = Game::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("gameId"),
                        onDelete = CASCADE)
        ))
data class Player (
        var name: String = "",
        var gameId: Long = 0
) : BaseEntity()