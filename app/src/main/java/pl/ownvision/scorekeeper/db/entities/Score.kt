package pl.ownvision.scorekeeper.db.entities

data class Score (
        var playerId: Long,
        var playerName: String,
        var points: Int,
        var moveCount: Int
)