package pl.ownvision.scorekeeper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.activityViewModel
import pl.ownvision.scorekeeper.core.BaseEpoxyFragment
import pl.ownvision.scorekeeper.core.MvRxEpoxyController

class StatsFragment : BaseEpoxyFragment() {

    private val viewModel : GameViewModel by activityViewModel(GameViewModel::class)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideFabButton()
    }

    override fun epoxyController(): MvRxEpoxyController {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}