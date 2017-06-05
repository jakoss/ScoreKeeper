package pl.ownvision.scorekeeper.views.activities

import activitystarter.Arg
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game.*
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.App
import pl.ownvision.scorekeeper.repositories.GameRepository
import pl.ownvision.scorekeeper.views.fragments.RoundsFragmentStarter
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
