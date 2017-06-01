package pl.ownvision.scorekeeper.views.activities

import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.github.nitrico.lastadapter.BR
import com.github.nitrico.lastadapter.LastAdapter
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.App
import pl.ownvision.scorekeeper.core.BaseActivity
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.*
import pl.ownvision.scorekeeper.databinding.ItemGameLayoutBinding
import pl.ownvision.scorekeeper.models.Game
import com.afollestad.materialdialogs.MaterialDialog
import android.text.InputType
import android.view.View
import android.widget.PopupMenu
import com.elvishew.xlog.XLog
import pl.ownvision.scorekeeper.core.snackbar
import pl.ownvision.scorekeeper.exceptions.*
import pl.ownvision.scorekeeper.repositories.GameRepository


class MainActivity : BaseActivity() {

    @Inject lateinit var application: App
    val games = ObservableArrayList<Game>()
    lateinit var lastAdapter: LastAdapter
    lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        gameRepository = GameRepository(realm)

        fab.setOnClickListener {
            createNewGame()
        }

        games_list.setHasFixedSize(true)
        games_list.layoutManager = LinearLayoutManager(this)

        lastAdapter = LastAdapter(games, BR.game, true)
                .map<Game, ItemGameLayoutBinding>(R.layout.item_game_layout) {
                    onClick {
                        val game = it.binding.game ?: return@onClick
                        // TODO : open game actvity
                        this@MainActivity.snackbar("Gra: ${game.name}")
                    }

                    onBind {
                        val game = it.binding.game ?: return@onBind
                        val view = it.itemView.findViewById(R.id.textViewOptions)
                        view.setOnClickListener {
                            displayPopup(it, game)
                        }
                    }
                }
                .into(games_list)

        games.addAll(gameRepository.getAllGames())
    }

    fun createNewGame(){
        MaterialDialog.Builder(this)
                .title("Nowa gra")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Nazwa...", null, { _, input ->
                    try {
                        val game = gameRepository.createGame(input.toString())
                        games.add(0, game)
                    }catch (e: ValidationException){
                        this.snackbar(e.error)
                    }catch (e: Exception){
                        this.snackbar("Błąd podczas tworzenia gry")
                        XLog.e("Error creating game", e)
                    }
                })
                .positiveText("Stwórz")
                .show()
    }

    fun displayPopup(view: View, game: Game){
        val popup = PopupMenu(view.context, view)
        popup.inflate(R.menu.menu_game)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_game_remove -> {
                    removeGame(game)
                    true
                }
                R.id.menu_game_rename -> {
                    renameGame(game)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    fun removeGame(game: Game){
        // TODO : confirm dialog
        try {
            gameRepository.removeGame(game)
            games.remove(game)
        }catch (e: Exception){
            this.snackbar("Błąd podczas usuwania gry")
            XLog.e("Error remove game", e)
        }
    }

    fun renameGame(game: Game){
        MaterialDialog.Builder(this)
                .title("Zmiana nazwy")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Nazwa...", game.name, { _, input ->
                    try {
                        game.name = input.toString()
                        gameRepository.updateGame(game)
                        lastAdapter.notifyDataSetChanged()
                    }catch (e: ValidationException){
                        this.snackbar(e.error)
                    }catch (e: Exception){
                        this.snackbar("Błąd podczas zmiany nazwy gry")
                        XLog.e("Error rename game", e)
                    }
                })
                .positiveText("Zapisz")
                .show()
    }
}
