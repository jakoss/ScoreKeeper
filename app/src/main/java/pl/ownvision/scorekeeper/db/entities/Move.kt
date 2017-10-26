package pl.ownvision.scorekeeper.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index

@Entity(tableName = "moves",
        foreignKeys = arrayOf(
                ForeignKey(entity = Game::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("gameId"),
                        onDelete = ForeignKey.CASCADE),
                ForeignKey(entity = Player::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("playerId"),
                        onDelete = ForeignKey.CASCADE)
        ),
        indices = arrayOf(Index("gameId"), Index("playerId")))
class Move: BaseEntity() {
    var score: Int = 0
    var gameId: Long = 0
    var playerId: Long = 0
    var playerName: String = ""
}