package pl.ownvision.scorekeeper.views.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.ObservableArrayList
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import co.metalab.asyncawait.async
import com.crashlytics.android.Crashlytics
import com.elvishew.xlog.XLog
import com.github.nitrico.lastadapter.BR
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.fragment_moves_list.*
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.alert
import pl.ownvision.scorekeeper.databinding.ItemMoveLayoutBinding
import pl.ownvision.scorekeeper.db.entities.Move
import pl.ownvision.scorekeeper.viewmodels.GameViewModel

class MovesFragment : BaseGameFragment() {

    private lateinit var lastAdapter: LastAdapter
    val moves = ObservableArrayList<Move>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(GameViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_moves_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moves_list.setHasFixedSize(true)
        moves_list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)

        lastAdapter = LastAdapter(moves, BR.move)
                .map<Move, ItemMoveLayoutBinding>(R.layout.item_move_layout) {
                    onBind {
                        val move = it.binding.move ?: return@onBind
                        val innerView = it.itemView.findViewById<View>(R.id.textViewOptions)
                        innerView.setOnClickListener {
                            displayPopup(it, move)
                        }
                    }
                }
                .into(moves_list)
        viewModel.getMoves().observe(this, Observer {
            if(it != null) {
                if(it.count() > 0){
                    moves_list.visibility = View.VISIBLE
                    tv_no_data.visibility = View.GONE

                    moves.clear()
                    moves.addAll(it)
                }else{
                    moves_list.visibility = View.GONE
                    tv_no_data.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun displayPopup(view: View, move: Move){
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

    private fun removeMove(move: Move){
        async {
            try {
                await { viewModel.deleteMove(move) }
            } catch (e: Exception) {
                activity.alert(R.string.error_deleting)
                XLog.e("Error removing move", e)
                Crashlytics.logException(e)
            }
        }
    }
}
