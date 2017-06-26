package pl.ownvision.scorekeeper.views.fragments

import pl.ownvision.scorekeeper.viewmodels.GameViewModel

/**
 * Created by Jakub on 26.06.2017.
 */

open class BaseGameFragment : BaseFragment() {
    protected lateinit var viewModel: GameViewModel
}