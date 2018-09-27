package pl.ownvision.scorekeeper.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index

@Entity(tableName = "players",
        foreignKeys = arrayOf(
                ForeignKey(entity = Game::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("gameId"),
                        onDelete = CASCADE)
        ),
        indices = arrayOf(Index("gameId")))
class Player: BaseEntity(){
    var name: String = ""
    var gameId: Long = 0
}