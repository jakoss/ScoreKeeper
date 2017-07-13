package pl.ownvision.scorekeeper.views.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import co.metalab.asyncawait.async
import com.afollestad.materialdialogs.MaterialDialog
import com.crashlytics.android.Crashlytics
import com.elvishew.xlog.XLog
import com.github.nitrico.lastadapter.BR
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.fragment_score.*
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.databinding.ItemScoreLayoutBinding
import pl.ownvision.scorekeeper.db.entities.Score
import pl.ownvision.scorekeeper.viewmodels.GameViewModel


class ScoreFragment : BaseGameFragment() {

    lateinit var dialog: MaterialDialog

    val scores = ObservableArrayList<Score>()
    lateinit var lastAdapter: LastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(GameViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_score, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        score_add_new_player.setOnClickListener {
            getGameActivity().redirectToPlayers()
        }

        setupPointsDialog()

        score_list.setHasFixedSize(true)
        score_list.layoutManager = LinearLayoutManager(activity)


        lastAdapter = LastAdapter(scores, BR.score)
                .map<Score, ItemScoreLayoutBinding>(R.layout.item_score_layout) {
                    onBind {
                        val score = it.binding.score ?: return@onBind
                        val button = it.itemView.findViewById(R.id.button_move)
                        button.setOnClickListener {
                            showMoveDialog(score)
                        }
                    }
                }
                .into(score_list)
        viewModel.getScores().observe(this, Observer {
            if(it != null){
                if(it.count() > 0) {
                    if(it.count() == scores.count()){
                        // soft reload (only changes)
                        for (i in  0..scores.count() - 1) {
                            scores[i] = it.find { it.playerId == scores[i].playerId }
                        }
                        scores.sortByDescending { it.points }
                        lastAdapter.notifyDataSetChanged()
                    }else {
                        // full list reload
                        scores.clear()
                        scores.addAll(it)
                    }
                }else{
                    score_list.visibility = View.GONE
                    tv_no_data.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showMoveDialog(score: Score) {
        val dialogView = dialog.customView ?: return
        val input = dialogView.findViewById(R.id.dialog_points_input) as EditText
        input.setText("0")
        val nameTextView = dialogView.findViewById(R.id.dialog_points_player) as TextView
        nameTextView.text = score.playerName
        dialog.builder.onPositive { _, _ ->
            async {
                try {
                    await { viewModel.addMove(score.playerId, input.text.toString().toInt())}
                } catch (e: Exception){
                    XLog.e("Error while creating move", e)
                    Crashlytics.logException(e)
                }
            }
        }
        dialog.show()
    }

    private fun setupPointsDialog(){
        dialog = MaterialDialog.Builder(activity)
                .customView(R.layout.dialog_points, true)
                .negativeText(R.string.cancel)
                .positiveText(R.string.add)
                .build()

        val dialogView = dialog.customView ?: return

        val input = dialogView.findViewById(R.id.dialog_points_input) as EditText

        fun setNewValue(button: View){
            val delta = button.tag.toString().toInt()
            val points = input.text.toString().toInt() + delta
            input.setText(points.toString())
        }

        val llAdd = dialogView.findViewById(R.id.dialog_points_ll_add) as LinearLayout
        val llSub = dialogView.findViewById(R.id.dialog_points_ll_sub) as LinearLayout

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
