package pl.ownvision.scorekeeper

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.afollestad.materialdialogs.MaterialDialog
import com.airbnb.mvrx.fragmentViewModel
import com.crashlytics.android.Crashlytics
import com.elvishew.xlog.XLog
import pl.ownvision.scorekeeper.core.*
import pl.ownvision.scorekeeper.db.entities.Game
import pl.ownvision.scorekeeper.exceptions.ValidationException
import pl.ownvision.scorekeeper.viewmodels.GameListViewModel
import pl.ownvision.scorekeeper.views.gameItemView

class GamesFragment : BaseFragment() {

    private val viewModel : GameListViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupErrorHandler(viewModel.errorSubject)
        fab.setOnClickListener {
            createNewGame()
        }
    }

    private fun createNewGame(){
        this.activity?.let {
            it.showInputDialog(R.string.new_game, R.string.create, getString(R.string.name_placeholder), null) { input ->
                try {
                    viewModel.addGame(input)
                } catch (e: ValidationException) {
                    this.alert(e.error)
                } catch (e: Exception) {
                    this.alert(R.string.error_creating)
                    XLog.e("Error creating game", e)
                    Crashlytics.logException(e)
                }
            }
        }
    }

    private fun displayPopup(view: View, game: Game){
        val popup = PopupMenu(view.context, view)
        popup.inflate(R.menu.menu_standard_item)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_standard_remove -> {
                    removeGame(game)
                    true
                }
                R.id.menu_standard_rename -> {
                    renameGame(game)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun removeGame(game: Game){
        this.activity?.let {
            MaterialDialog(it)
                    .title(R.string.remove_confirm)
                    .positiveButton(R.string.yes) { _ ->
                        try {
                            viewModel.removeGame(game)
                        } catch (e: Exception) {
                            this.alert(R.string.error_deleting)
                            XLog.e("Error removing game", e)
                            Crashlytics.logException(e)
                        }
                    }
                    .negativeButton(R.string.no)
                    .show()
        }
    }

    private fun renameGame(game: Game){
        this.activity?.let {
            it.showInputDialog(R.string.rename, R.string.save, getString(R.string.name_placeholder), game.name) {input ->
                try {
                    viewModel.renameGame(game, input)
                } catch (e: ValidationException) {
                    this.alert(e.error)
                } catch (e: Exception) {
                    this.alert(R.string.error_renaming)
                    XLog.e("Error rename game", e)
                    Crashlytics.logException(e)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_activity_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId ?: return super.onOptionsItemSelected(item)
        return when (itemId){
            R.id.about_application -> {
                showAbout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        if (state.databaseRequest.complete) {
            state.games.forEach { game ->
                gameItemView {
                    id(game.id)
                    name(game.name)
                    createdAt(game.getCreatedLocal())
                    onItemClick { _ ->
                        // TODO : handle navigation to game
                    }
                    onOptionsClick { view ->
                        displayPopup(view, game)
                    }
                }
            }
        }
    }
}