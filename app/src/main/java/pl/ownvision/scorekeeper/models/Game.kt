package pl.ownvision.scorekeeper.models

import com.github.nitrico.lastadapter.StableId
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import pl.ownvision.scorekeeper.core.getFormattedLocal
import java.util.*

/**
 * Created by jakub on 31.05.2017.
 */

open class Game (
        @PrimaryKey
        var id: String = UUID.randomUUID().toString(),

        @Required
        var name: String = "",

        override var createdAt: Date = Date(),

        var players: RealmList<Player> = RealmList(),
        var moves: RealmList<Move> = RealmList(),

        // stable id for LastAdapter
        @Ignore
        override val stableId: Long = id.hashCode().toLong()
) : RealmObject(), StableId, CreatedAt