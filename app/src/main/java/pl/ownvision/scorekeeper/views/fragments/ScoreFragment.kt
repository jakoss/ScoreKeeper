package pl.ownvision.scorekeeper.views.fragments

import activitystarter.ActivityStarter
import activitystarter.Arg
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
import kotlinx.android.synthetic.main.fragment_score.*
import pl.ownvision.scorekeeper.R
import com.afollestad.materialdialogs.MaterialDialog
import com.github.nitrico.lastadapter.BR
import com.github.nitrico.lastadapter.LastAdapter
import pl.ownvision.scorekeeper.core.App
import pl.ownvision.scorekeeper.core.alert
import pl.ownvision.scorekeeper.databinding.ItemScoreLayoutBinding
import pl.ownvision.scorekeeper.models.Player
import pl.ownvision.scorekeeper.models.Score
import pl.ownvision.scorekeeper.repositories.ScoresRepository
import javax.inject.Inject


class ScoreFragment : BaseFragment() {

    @Arg lateinit var gameId: String

    @Inject lateinit var scoresRepository: ScoresRepository
    lateinit var dialog: MaterialDialog

    val scores = ObservableArrayList<Score>()
    lateinit var lastAdapter: LastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        scoresRepository.gameId = gameId
    }

    override fun onDestroy() {
        super.onDestroy()
        scoresRepository.closeRealm()
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
                            showMoveDialog(score.player)
                        }
                    }
                }
                .into(score_list)
    }

    override fun onStart() {
        super.onStart()
        val game = gameRepository.getGame(gameId)
        val players = game.players
        if(players.count() == 0){
            // Empty list
            score_list.visibility = View.GONE
            tv_no_data.visibility = View.VISIBLE
        }else{
            // data present!
            score_list.visibility = View.VISIBLE
            tv_no_data.visibility = View.GONE

            loadScoreList()
        }
    }

    private fun loadScoreList() {
        scores.clear()
        val scoresList = scoresRepository.getScoresList()
        scores.addAll(scoresList.sortedByDescending { it.points })
    }

    private fun updateScoreList() {
        val scoresList = scoresRepository.getScoresList()
        scoresList.forEach {
            val player = it.player
            val score = scores.find { it.player.id == player.id }
            if(score != null) {
                score.points = it.points
                score.moveCount = it.moveCount
            }
        }
        scores.sortByDescending { it.points }
        lastAdapter.notifyDataSetChanged()
    }

    private fun showMoveDialog(player: Player) {
        val dialogView = dialog.customView ?: return
        val input = dialogView.findViewById(R.id.dialog_points_input) as EditText
        input.setText("0")
        val nameTextView = dialogView.findViewById(R.id.dialog_points_player) as TextView
        nameTextView.text = player.name
        dialog.builder.onPositive { _, _ ->
            scoresRepository.createMove(player, input.text.toString().toInt())
            updateScoreList()
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
