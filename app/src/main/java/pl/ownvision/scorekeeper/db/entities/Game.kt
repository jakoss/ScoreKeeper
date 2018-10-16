package pl.ownvision.scorekeeper.db.entities

import androidx.room.*

@Entity(tableName = "games")
data class Game(var name: String) : BaseEntity()