package pl.ownvision.scorekeeper.db.entities

import androidx.room.PrimaryKey
import org.joda.time.DateTime
import pl.ownvision.scorekeeper.core.getFormattedLocal

abstract class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var createdAt: DateTime = DateTime()

    fun getCreatedLocal() = createdAt.getFormattedLocal()
}