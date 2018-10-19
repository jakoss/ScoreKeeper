package pl.ownvision.scorekeeper

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import pl.ownvision.scorekeeper.core.ScreenEnum
import pl.ownvision.scorekeeper.db.entities.*

data class GameState(
        val screen: ScreenEnum = ScreenEnum.SCORE,
        val scores: Async<List<Score>> = Uninitialized,
        val moves: Async<List<Move>> = Uninitialized,
        val timeline: Async<List<Move>> = Uninitialized
) : MvRxState