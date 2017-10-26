package pl.ownvision.scorekeeper.views.fragments

import pl.ownvision.scorekeeper.viewmodels.GameViewModel

open class BaseGameFragment : BaseFragment() {
    protected lateinit var viewModel: GameViewModel
}