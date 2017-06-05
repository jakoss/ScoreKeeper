package pl.ownvision.scorekeeper.views.fragments

import activitystarter.ActivityStarter
import android.os.Bundle
import android.support.v4.app.Fragment
import pl.ownvision.scorekeeper.core.App
import pl.ownvision.scorekeeper.repositories.GameRepository
import javax.inject.Inject

/**
 * Created by jakub on 05.06.2017.
 */
open class BaseFragment : Fragment() {

    @Inject lateinit protected var gameRepository: GameRepository
    @Inject lateinit protected var application: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityStarter.fill(this)
        App.appComponent.inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        gameRepository.closeRealm()
    }
}