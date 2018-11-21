package pl.ownvision.scorekeeper

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import com.airbnb.mvrx.activityViewModel
import pl.ownvision.scorekeeper.core.BaseEpoxyFragment
import pl.ownvision.scorekeeper.core.simpleController
import pl.ownvision.scorekeeper.db.entities.Move
import pl.ownvision.scorekeeper.views.moveItemView
import pl.ownvision.scorekeeper.views.viewLoader
import pl.ownvision.scorekeeper.views.viewNoData

class MovesFragment : BaseEpoxyFragment() {

    private val viewModel : GameViewModel by activityViewModel(GameViewModel::class)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideFabButton()
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        if (!state.moves.complete) {
            viewLoader {
                id("loader")
            }
            return@simpleController
        }
        val moves = state.moves()
        if (moves.isNullOrEmpty()){
            viewNoData {
                id("viewNoPlayer")
                information(R.string.no_moves_information)
            }
            return@simpleController
        }

        moves.sortedByDescending { it.createdAt }.forEach { move ->
            moveItemView {
                id(move.id)
                title(getString(R.string.score_title, move.playerName, move.score))
                createdAt(move.getCreatedLocal())
                onOptionsClick { view ->
                    displayPopup(view, move)
                }
            }
        }
    }

    private fun displayPopup(view: View, move: Move){
        val popup = PopupMenu(view.context, view)
        popup.inflate(R.menu.menu_standard_item)

        popup.menu.removeItem(R.id.menu_standard_rename)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_standard_remove -> {
                    viewModel.removeMove(move)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}