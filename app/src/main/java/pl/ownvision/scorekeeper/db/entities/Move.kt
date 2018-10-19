package pl.ownvision.scorekeeper.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "moves",
        foreignKeys = [ForeignKey(entity = Player::class,
                parentColumns = ["id"],
                childColumns = ["playerId"],
                onDelete = ForeignKey.CASCADE)],
        indices = [Index("playerId")])
data class Move(
        var score: Int,
        var playerId: Long,
        var playerName: String
): BaseEntity()