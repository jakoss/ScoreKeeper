package pl.ownvision.scorekeeper

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.transaction
import com.afollestad.materialdialogs.MaterialDialog
import com.airbnb.mvrx.BaseMvRxActivity
import com.airbnb.mvrx.viewModel
import com.crashlytics.android.Crashlytics
import com.elvishew.xlog.XLog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import pl.ownvision.scorekeeper.core.ScreenEnum
import pl.ownvision.scorekeeper.core.alert
import pl.ownvision.scorekeeper.core.showAbout
import pl.ownvision.scorekeeper.core.showConfirmationDialog
import pl.ownvision.scorekeeper.exceptions.ValidationException

class MainActivity : BaseMvRxActivity() {

    private val gameViewModel : GameViewModel by viewModel(GameViewModel::class)
    private var errorHandler: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.navigationIcon = null
        supportActionBar?.let { it.title = getString(R.string.app_name) }

        errorHandler = gameViewModel.errorSubject.subscribe { exception ->
            if (exception is ValidationException){
                this.alert(exception.error)
            }else {
                this.alert(exception.localizedMessage)
                XLog.e("Error", exception)
                Crashlytics.logException(exception)
            }
        }

        gameViewModel.selectSubscribe(this, GameState::screen) { screen ->
            val fragment = when (screen) {
                // TODO : pin proper fragments
                ScreenEnum.SCORE -> ScoresFragment()
                ScreenEnum.HISTORY -> ScoresFragment()
                ScreenEnum.STATS -> ScoresFragment()
            }
            supportFragmentManager.transaction {
                replace(R.id.fragment_container, fragment)
            }
        }

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val screen = when (item.itemId) {
                R.id.menu_game_score -> ScreenEnum.STATS
                R.id.menu_game_rounds -> ScreenEnum.HISTORY
                R.id.menu_game_stats -> ScreenEnum.STATS
                else -> ScreenEnum.STATS
            }
            gameViewModel.changeScreen(screen)
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        errorHandler?.dispose()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId ?: return super.onOptionsItemSelected(item)
        return when (itemId){
            R.id.about_application -> {
                showAbout()
                true
            }
            R.id.reset_game -> {
                showConfirmationDialog(R.string.reset_game_prompt) {
                    gameViewModel.resetGame()
                }
                true
            }
            R.id.reset_score -> {
                showConfirmationDialog(R.string.reset_score_prompt) {
                    gameViewModel.resetScore()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}