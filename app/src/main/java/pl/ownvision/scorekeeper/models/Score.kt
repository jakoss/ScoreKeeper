package pl.ownvision.scorekeeper.models

/**
 * Created by jakub on 07.06.2017.
 */

class Score (
        val player: Player,
        var points: Int,
        var moveCount: Int
){
    fun getMoveCountString() = "Ilość wykonanych ruchów: $moveCount" // TODO : extract string to resources
    fun getTitle() = "${player.name}: $points"
}