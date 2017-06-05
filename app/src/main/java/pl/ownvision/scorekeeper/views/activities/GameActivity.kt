package pl.ownvision.scorekeeper.views.activities

import activitystarter.Arg
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game.*
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.App
import pl.ownvision.scorekeeper.repositories.GameRepository
import pl.ownvision.scorekeeper.views.fragments.PlayersFragmentStarter
import pl.ownvision.scorekeeper.views.fragments.RoundsFragmentStarter
import pl.ownvision.scorekeeper.views.fragments.StatsFragmentStarter
import javax.inject.Inject


class GameActivity : BaseActivity() {

    @Arg lateinit var gameId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        setToolbar(toolbar_include)

        val game = gameRepository.getGame(gameId)
        supportActionBar!!.title = game.name

        setupFragment(savedInstanceState)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.menu_game_rounds -> RoundsFragmentStarter.newInstance(gameId)
                R.id.menu_game_players -> PlayersFragmentStarter.newInstance(gameId)
                R.id.menu_game_stats -> StatsFragmentStarter.newInstance(gameId)
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
        val roundsFragment = RoundsFragmentStarter.newInstance(gameId)
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, roundsFragment)
                .commit()
    }
}
