package pl.ownvision.scorekeeper.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index

@Entity(tableName = "players",
        foreignKeys = [ForeignKey(entity = Game::class,
                parentColumns = ["id"],
                childColumns = ["gameId"],
                onDelete = CASCADE)],
        indices = [Index("gameId")])
data class Player(
        var name: String,
        var gameId: Long
): BaseEntity()