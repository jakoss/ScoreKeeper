package pl.ownvision.scorekeeper.views.activities

import activitystarter.Arg
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import co.metalab.asyncawait.async
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
}
