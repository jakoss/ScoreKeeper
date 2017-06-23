package pl.ownvision.scorekeeper.db.entities

import android.arch.persistence.room.*

/**
 * Created by jakub on 31.05.2017.
 */

@Entity(tableName = "games")
class Game : BaseEntity() {
    var name: String = ""
}