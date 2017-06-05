package pl.ownvision.scorekeeper.models

import com.github.nitrico.lastadapter.StableId
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by jakub on 31.05.2017.
 */

open class Round(
        @PrimaryKey
        var id: String = UUID.randomUUID().toString(),

        var createdAt: java.util.Date = Date(),

        // stable id for LastAdapter
        @Ignore
        override val stableId: Long = id.hashCode().toLong()
) : RealmObject(), StableId