package pl.ownvision.scorekeeper.views.activities

import activitystarter.Arg
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import co.metalab.asyncawait.async
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import kotlinx.android.synthetic.main.activity_game.*
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.viewmodels.GameViewModel
import pl.ownvision.scorekeeper.views.fragments.*


class GameActivity : BaseActivity() {

    @Arg var gameId: Long = 0

    lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        setToolbar(toolbar_include)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GameViewModel::class.java)
        viewModel.init(gameId)

        async {
            val title = await { viewModel.getGameTitle() }
            supportActionBar!!.title = title
        }

        setupFragment(savedInstanceState)

        // TODO : change to viewpager
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.menu_game_score -> ScoreFragment()
                R.id.menu_game_rounds -> MovesFragment()
                R.id.menu_game_players -> PlayersFragment()
                R.id.menu_game_stats -> StatsFragment()
                else -> null
            }
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            true
        }
    }

    fun setupFragment(savedInstanceState: Bundle?){
        if(savedInstanceState != null) return
        val scoreFragment = ScoreFragment()
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, scoreFragment)
                .commit()
    }

    fun redirectToPlayers() {
        val fragment = PlayersFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        bottom_navigation.selectedItemId = R.id.menu_game_players
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId ?: return super.onOptionsItemSelected(item)
        when (itemId){
            R.id.about_application -> {
                LibsBuilder()
                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                        .withAboutIconShown(true)
                        .withAboutVersionShown(true)
                        .start(this)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
