package pl.ownvision.scorekeeper.db.entities

/**
 * Created by jakub on 07.06.2017.
 */

class Score (
        var playerId: Long,
        var playerName: String,
        var points: Int,
        var moveCount: Int
)