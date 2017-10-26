package pl.ownvision.scorekeeper.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Index

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