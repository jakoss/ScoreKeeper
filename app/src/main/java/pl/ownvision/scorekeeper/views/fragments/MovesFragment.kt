package pl.ownvision.scorekeeper.views.fragments

import activitystarter.Arg
import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.elvishew.xlog.XLog
import com.github.nitrico.lastadapter.BR
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.fragment_moves_list.*
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.App
import pl.ownvision.scorekeeper.core.snackbar
import pl.ownvision.scorekeeper.databinding.ItemMoveLayoutBinding
import pl.ownvision.scorekeeper.models.Move
import pl.ownvision.scorekeeper.repositories.ScoresRepository
import javax.inject.Inject


class MovesFragment : BaseFragment() {

    @Arg lateinit var gameId: String

    @Inject lateinit var scoresRepository: ScoresRepository

    lateinit var lastAdapter: LastAdapter
    val moves = ObservableArrayList<Move>()

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
        return inflater!!.inflate(R.layout.fragment_moves_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moves_list.setHasFixedSize(true)
        moves_list.layoutManager = LinearLayoutManager(activity)

        lastAdapter = LastAdapter(moves, BR.move)
                .map<Move, ItemMoveLayoutBinding>(R.layout.item_move_layout) {
                    onBind {
                        val move = it.binding.move ?: return@onBind
                        val view = it.itemView.findViewById(R.id.textViewOptions)
                        view.setOnClickListener {
                            displayPopup(it, move)
                        }
                    }
                }
                .into(moves_list)
    }

    override fun onStart() {
        super.onStart()
        val game = gameRepository.getGame(gameId)
        moves.addAll(game.moves.sortedByDescending { it.createdAt })

        if(moves.count() == 0){
            // Empty list
            moves_list.visibility = View.GONE
            tv_no_data.visibility = View.VISIBLE
        }else{
            // data present!
            moves_list.visibility = View.VISIBLE
            tv_no_data.visibility = View.GONE
        }
    }

    fun displayPopup(view: View, move: Move){
        val popup = PopupMenu(view.context, view)
        popup.inflate(R.menu.menu_standard_item)

        popup.menu.removeItem(R.id.menu_standard_rename)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_standard_remove -> {
                    removeMove(move)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    fun removeMove(move: Move){
        try {
            moves.remove(move)
            scoresRepository.removeMove(move)
        }catch (e: Exception){
            activity.snackbar(R.string.error_deleting)
            XLog.e("Error removing move", e)
        }
    }
}
