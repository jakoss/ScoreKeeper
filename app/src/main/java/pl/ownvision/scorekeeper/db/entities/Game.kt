package pl.ownvision.scorekeeper.db.entities

import android.arch.persistence.room.*

@Entity(tableName = "games")
class Game : BaseEntity() {
    var name: String = ""
}