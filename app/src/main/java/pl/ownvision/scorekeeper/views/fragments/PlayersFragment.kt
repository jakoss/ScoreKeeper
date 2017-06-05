package pl.ownvision.scorekeeper.views.fragments

import activitystarter.Arg
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_rounds_list.*
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.snackbar


class PlayersFragment : BaseFragment() {

    @Arg lateinit var gameId: String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_players_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val game = gameRepository.getGame(gameId)

        fab.setOnClickListener {
            activity.snackbar(game.name)
        }
    }

    override fun onStart() {
        super.onStart()
        // TODO : load players list
    }
}
