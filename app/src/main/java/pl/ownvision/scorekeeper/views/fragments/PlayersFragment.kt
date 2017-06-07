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
import kotlinx.android.synthetic.main.fragment_players_list.*
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.App
import pl.ownvision.scorekeeper.core.alert
import pl.ownvision.scorekeeper.core.showInputDialog
import pl.ownvision.scorekeeper.core.snackbar
import pl.ownvision.scorekeeper.databinding.ItemPlayerLayoutBinding
import pl.ownvision.scorekeeper.exceptions.ValidationException
import pl.ownvision.scorekeeper.models.Player
import pl.ownvision.scorekeeper.repositories.PlayerRepository
import javax.inject.Inject


class PlayersFragment : BaseFragment() {

    @Arg lateinit var gameId: String

    @Inject lateinit var playerRepository: PlayerRepository

    val players = ObservableArrayList<Player>()
    lateinit var lastAdapter: LastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        playerRepository.gameId = gameId
    }

    override fun onDestroy() {
        super.onDestroy()
        playerRepository.closeRealm()
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
                        val view = it.itemView.findViewById(R.id.textViewOptions)
                        view.setOnClickListener {
                            displayPopup(it, player)
                        }
                    }
                }
                .into(players_list)

        if(!playerRepository.canUpdate()) {
            fab.hide()
            activity.alert("Dodawanie i usuwanie dostępne tylko przed startem rozgrywki")
        }
    }

    override fun onStart() {
        super.onStart()
        players.clear()
        players.addAll(playerRepository.getPlayers())
    }

    fun displayPopup(view: View, player: Player){
        val popup = PopupMenu(view.context, view)
        popup.inflate(R.menu.menu_standard_item)

        if(!playerRepository.canUpdate())
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
            try {
                val player = playerRepository.createNewPlayer(input.toString())
                players.add(player)
            }catch (e: ValidationException){
                activity.snackbar(e.error)
            }catch (e: Exception){
                activity.snackbar(R.string.error_creating)
                XLog.e("Error creating player", e)
            }
        }
    }

    fun removePlayer(player: Player){
        // TODO : confirm dialog
        try {
            playerRepository.removePlayer(player.id)
            players.remove(player)
        }catch (e: Exception){
            activity.snackbar(R.string.error_deleting)
            XLog.e("Error removing player", e)
        }
    }

    fun renamePlayer(player: Player){
        activity.showInputDialog(R.string.rename, R.string.save, getString(R.string.name_placeholder), player.name) {input ->
            try {
                player.name = input.toString()
                playerRepository.updatePlayer(player)
                lastAdapter.notifyDataSetChanged()
            }catch (e: ValidationException){
                activity.snackbar(e.error)
            }catch (e: Exception){
                activity.snackbar(R.string.error_renaming)
                XLog.e("Error rename player", e)
            }
        }
    }
}
