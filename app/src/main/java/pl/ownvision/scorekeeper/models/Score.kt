package pl.ownvision.scorekeeper.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

/**
 * Created by jakub on 31.05.2017.
 */

open class Score(
        @PrimaryKey
        var id: String = UUID.randomUUID().toString(),

        var player: Player? = null,
        var createdAt: java.util.Date = Date(),

        var score: Long = 0
) : RealmObject()