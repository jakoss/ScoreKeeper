package pl.ownvision.scorekeeper.views.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import co.metalab.asyncawait.async
import com.elvishew.xlog.XLog
import com.github.nitrico.lastadapter.BR
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.fragment_players_list.*
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.alert
import pl.ownvision.scorekeeper.core.showInputDialog
import pl.ownvision.scorekeeper.core.snackbar
import pl.ownvision.scorekeeper.databinding.ItemPlayerLayoutBinding
import pl.ownvision.scorekeeper.db.entities.Player
import pl.ownvision.scorekeeper.exceptions.ValidationException
import pl.ownvision.scorekeeper.viewmodels.GameViewModel


class PlayersFragment : BaseGameFragment() {

    val players = ObservableArrayList<Player>()
    var canUpdate: Boolean = true
    lateinit var lastAdapter: LastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(GameViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_players_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            addPlayer()
        }

        players_list.setHasFixedSize(true)
        players_list.layoutManager = LinearLayoutManager(activity)

        lastAdapter = LastAdapter(players, BR.player, true)
                .map<Player, ItemPlayerLayoutBinding>(R.layout.item_player_layout) {
                    onBind {
                        val player = it.binding.player ?: return@onBind
                        val innerView = it.itemView.findViewById(R.id.textViewOptions)
                        innerView.setOnClickListener {
                            displayPopup(it, player)
                        }
                    }
                }
                .into(players_list)

        fab.hide()
        async {
            canUpdate = await { viewModel.canEditPlayers() }
            if(canUpdate) {
                fab?.show()
            }else{
                activity.alert("Dodawanie i usuwanie dostępne tylko przed startem rozgrywki")
            }
        }

        viewModel.getPlayers().observe(this, Observer {
            if(it != null) {
                players.clear()
                players.addAll(it)
            }
        })
    }

    fun displayPopup(view: View, player: Player){
        val popup = PopupMenu(view.context, view)
        popup.inflate(R.menu.menu_standard_item)

        if(!canUpdate)
            popup.menu.removeItem(R.id.menu_standard_remove)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_standard_remove -> {
                    removePlayer(player)
                    true
                }
                R.id.menu_standard_rename -> {
                    renamePlayer(player)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    fun addPlayer(){
        activity.showInputDialog(R.string.new_player, R.string.create, getString(R.string.name_placeholder), null) {input ->
            async {
                try {
                    await { viewModel.addPlayer(input)}
                } catch (e: ValidationException) {
                    activity.alert(e.error)
                } catch (e: Exception) {
                    activity.alert(R.string.error_creating)
                    XLog.e("Error creating player", e)
                }
            }
        }
    }

    fun removePlayer(player: Player){
        async {
            try {
                await { viewModel.removePlayer(player) }
            } catch (e: Exception) {
                activity.alert(R.string.error_deleting)
                XLog.e("Error removing player", e)
            }
        }
    }

    fun renamePlayer(player: Player){
        activity.showInputDialog(R.string.rename, R.string.save, getString(R.string.name_placeholder), player.name) {input ->
            async {
                try {
                    await { viewModel.renamePlayer(player, input) }
                } catch (e: ValidationException) {
                    activity.alert(e.error)
                } catch (e: Exception) {
                    activity.alert(R.string.error_renaming)
                    XLog.e("Error rename player", e)
                }
            }
        }
    }
}
