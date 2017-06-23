package pl.ownvision.scorekeeper.models

import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.App

/**
 * Created by jakub on 07.06.2017.
 */

class Score (
        val player: Player,
        var points: Int,
        var moveCount: Int
)