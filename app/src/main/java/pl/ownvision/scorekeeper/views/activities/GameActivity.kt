package pl.ownvision.scorekeeper.views.activities

import activitystarter.Arg
import activitystarter.Optional
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import co.metalab.asyncawait.async
import kotlinx.android.synthetic.main.activity_game.*
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.showAbout
import pl.ownvision.scorekeeper.viewmodels.GameViewModel
import pl.ownvision.scorekeeper.views.fragments.*


class GameActivity : BaseActivity() {

    @Arg
    var gameId: Long = 0

    @Arg
    @Optional
    var pageNumber: Int = -1

    lateinit var viewModel: GameViewModel
    lateinit var fragmentsArray: List<BaseFragment>

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

        fragmentsArray = listOf(
                ScoreFragment(),
                MovesFragment(),
                PlayersFragment(),
                StatsFragment()
        )

        setupFragment(savedInstanceState)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val fragmentIndex = when (item.itemId) {
                R.id.menu_game_score -> 0
                R.id.menu_game_rounds -> 1
                R.id.menu_game_players -> 2
                R.id.menu_game_stats -> 3
                else -> 0
            }
            val fragment = fragmentsArray[fragmentIndex]
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            pageNumber = fragmentIndex
            true
        }
    }

    fun setupFragment(savedInstanceState: Bundle?){
        if(savedInstanceState != null) return
        if(pageNumber > -1){
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragmentsArray[pageNumber])
                    .commit()
        }else {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragmentsArray[0])
                    .commit()
        }
    }

    fun redirectToPlayers() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentsArray[2])
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
                showAbout()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
