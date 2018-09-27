package pl.ownvision.scorekeeper.db.entities

import androidx.room.*

@Entity(tableName = "games")
class Game : BaseEntity() {
    var name: String = ""
}