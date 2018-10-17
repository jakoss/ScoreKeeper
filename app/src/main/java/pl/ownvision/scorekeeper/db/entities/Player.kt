package pl.ownvision.scorekeeper.db.entities

import androidx.room.Entity

@Entity(tableName = "players")
data class Player(
        var name: String
): BaseEntity()