package pl.ownvision.scorekeeper.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import pl.ownvision.scorekeeper.core.getFormattedLocal
import java.util.*

/**
 * Created by jakub on 31.05.2017.
 */

open class Move(
        @PrimaryKey
        var id: String = UUID.randomUUID().toString(),

        var player: Player? = null,
        override var createdAt: java.util.Date = Date(),

        var score: Int = 0
) : RealmObject(), CreatedAt