package pl.ownvision.scorekeeper.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

/**
 * Created by jakub on 31.05.2017.
 */

@Entity(tableName = "moves",
        foreignKeys = arrayOf(
                ForeignKey(entity = Game::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("gameId"),
                        onDelete = ForeignKey.CASCADE)
        ))
data class Move(
        var player: Player? = null,
        var score: Int = 0,
        var gameId: Long = 0
) : BaseEntity()