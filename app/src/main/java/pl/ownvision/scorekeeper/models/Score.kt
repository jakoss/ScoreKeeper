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
){
    fun getMoveCountString(): String = App.instance.getString(R.string.move_count, moveCount)
    fun getTitle() = "${player.name}: $points"
}