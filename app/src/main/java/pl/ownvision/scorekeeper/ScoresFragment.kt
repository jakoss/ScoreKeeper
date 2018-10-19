package pl.ownvision.scorekeeper

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.airbnb.mvrx.activityViewModel
import pl.ownvision.scorekeeper.core.*
import pl.ownvision.scorekeeper.db.entities.Score
import pl.ownvision.scorekeeper.views.scoreItemView
import pl.ownvision.scorekeeper.views.viewLoader
import pl.ownvision.scorekeeper.views.viewNoPlayer

class ScoresFragment : BaseEpoxyFragment() {

    private val viewModel : GameViewModel by activityViewModel(GameViewModel::class)

    private lateinit var dialog: MaterialDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFabButton {
            addPlayer()
        }
        setupPointsDialog()
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        if (!state.scores.complete) {
            viewLoader {
                id("loader")
            }
            return@simpleController
        }
        val scores = state.scores()
        if (scores.isNullOrEmpty()){
            viewNoPlayer {
                id("viewNoPlayer")
                onClick { _ ->
                    addPlayer()
                }
            }
            return@simpleController
        }

        // the !! operator won't be needed after Kotlin 1.3 release
        scores!!.sortedByDescending { it.points }.forEach { score ->
            scoreItemView {
                id(score.playerId)
                title(R.string.score_title, score.playerName, score.points)
                moveCount(R.string.move_count, score.moveCount)
                onMoveClick { _ ->
                    showMoveDialog(score)
                }
            }
        }
    }

    private fun addPlayer(){
        requireActivity().showInputDialog(R.string.new_player, R.string.create, getString(R.string.name_placeholder), null) {input ->
            viewModel.addPlayer(input)
        }
    }

    private fun showMoveDialog(score: Score) {
        val dialogView = dialog.getCustomView() ?: return
        val input = dialogView.findViewById<EditText>(R.id.dialog_points_input)
        input.setText("0")
        val nameTextView = dialogView.findViewById<TextView>(R.id.dialog_points_player)
        nameTextView.text = score.playerName
        /*
        dialog.positiveButton {
            viewModel.addMove(score.playerId, input.text.toString().toInt())
        }
        */
        dialog.show()
        dialog.getActionButton(WhichButton.POSITIVE).setOnClickListener {
            viewModel.addMove(score.playerId, input.text.toString().toInt())
            dialog.dismiss()
        }
    }

    private fun setupPointsDialog(){
        dialog = MaterialDialog(requireContext())
                .customView(R.layout.dialog_points)
                .negativeButton(R.string.cancel)
                .positiveButton(R.string.add)

        val dialogView = dialog.getCustomView() ?: return

        val input = dialogView.findViewById<EditText>(R.id.dialog_points_input)

        fun setNewValue(button: View){
            val delta = button.tag.toString().toInt()
            val points = input.text.toString().toInt() + delta
            input.setText(points.toString())
        }

        val llAdd = dialogView.findViewById<LinearLayout>(R.id.dialog_points_ll_add)
        val llSub = dialogView.findViewById<LinearLayout>(R.id.dialog_points_ll_sub)

        for (i in 0..llAdd.childCount){
            val buttonAdd = llAdd.getChildAt(i) as Button?
            val buttonSub = llSub.getChildAt(i) as Button?
            buttonAdd?.setOnClickListener {
                setNewValue(it)
            }
            buttonSub?.setOnClickListener {
                setNewValue(it)
            }
        }
    }
}