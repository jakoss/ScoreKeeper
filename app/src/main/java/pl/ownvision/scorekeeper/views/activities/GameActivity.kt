package pl.ownvision.scorekeeper.views.activities

import activitystarter.Arg
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.App
import pl.ownvision.scorekeeper.core.BaseActivity
import pl.ownvision.scorekeeper.core.snackbar
import pl.ownvision.scorekeeper.repositories.GameRepository
import javax.inject.Inject


class GameActivity : BaseActivity() {

    @Arg lateinit var gameId: String

    @Inject lateinit var application: App
    @Inject lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        setContentView(R.layout.activity_game)
        setSupportActionBar(toolbar)

        val game = gameRepository.getGame(gameId)
        supportActionBar!!.title = game.name

        fab.setOnClickListener {
            // TODO : Create new round
            this.snackbar(game.id)
        }
    }
}
