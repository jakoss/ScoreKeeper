package pl.ownvision.scorekeeper

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.activityViewModel
import pl.ownvision.scorekeeper.core.*
import pl.ownvision.scorekeeper.views.scoreItemView
import pl.ownvision.scorekeeper.views.viewLoader
import pl.ownvision.scorekeeper.views.viewNoPlayer

class ScoresFragment : BaseEpoxyFragment() {

    private val viewModel : GameViewModel by activityViewModel(GameViewModel::class)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFabButton {
            addPlayer()
        }
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        if (state.scores.complete) {
            val scores = state.scores()
            if (scores.isNullOrEmpty()){
                viewNoPlayer {
                    id("viewNoPlayer")
                    onClick { _ ->
                        addPlayer()
                    }
                }
            }else{
                // the !! operator won't be needed after Kotlin 1.3 release
                scores!!.forEach { score ->
                    scoreItemView {
                        id(score.playerId)
                        title(R.string.score_title, score.playerName, score.points)
                        moveCount(R.string.move_count, score.moveCount)
                        onMoveClick { _ ->
                            // TODO : add new move
                        }
                    }
                }
            }
        } else {
            viewLoader {
                id("loader")
            }
        }
    }

    private fun addPlayer(){
        requireActivity().showInputDialog(R.string.new_player, R.string.create, getString(R.string.name_placeholder), null) {input ->
            viewModel.addPlayer(input)
        }
    }
}