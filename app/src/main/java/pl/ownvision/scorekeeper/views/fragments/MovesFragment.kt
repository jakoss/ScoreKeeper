package pl.ownvision.scorekeeper.views.fragments

import activitystarter.Arg
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.fragment_moves_list.*
import pl.ownvision.scorekeeper.R


class MovesFragment : BaseFragment() {

    @Arg lateinit var gameId: String

    lateinit var adapter: LastAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_moves_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val game = gameRepository.getGame(gameId)
        val moves = game.moves

        if(moves.count() == 0){
            // Empty list
            moves_list.visibility = View.GONE
            tv_no_data.visibility = View.VISIBLE
        }else{
            // data present!
            moves_list.visibility = View.VISIBLE
            tv_no_data.visibility = View.GONE

            // adapter = LastAdapter()
        }
    }
}
