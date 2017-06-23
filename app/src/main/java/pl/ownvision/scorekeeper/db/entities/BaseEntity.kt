package pl.ownvision.scorekeeper.db.entities

import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.github.nitrico.lastadapter.StableId
import org.joda.time.DateTime
import pl.ownvision.scorekeeper.core.getFormattedLocal

/**
 * Created by jakub on 23.06.2017.
 */

abstract class BaseEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        var createdAt: DateTime = DateTime.now(),

        // stable id for LastAdapter
        @Ignore
        override val stableId: Long = id
) : StableId {
    fun getCreatedLocal() = createdAt.getFormattedLocal()
}