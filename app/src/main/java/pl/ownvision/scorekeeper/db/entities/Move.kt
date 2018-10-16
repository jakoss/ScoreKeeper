package pl.ownvision.scorekeeper.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "moves",
        foreignKeys = [ForeignKey(entity = Game::class,
                parentColumns = ["id"],
                childColumns = ["gameId"],
                onDelete = ForeignKey.CASCADE), ForeignKey(entity = Player::class,
                parentColumns = ["id"],
                childColumns = ["playerId"],
                onDelete = ForeignKey.CASCADE)],
        indices = [Index("gameId"), Index("playerId")])
data class Move(
        var score: Int,
        var gameId: Long,
        var playerId: Long,
        var playerName: String
): BaseEntity()