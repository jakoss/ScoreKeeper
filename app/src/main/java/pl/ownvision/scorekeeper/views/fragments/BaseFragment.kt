package pl.ownvision.scorekeeper.views.fragments

import activitystarter.ActivityStarter
import activitystarter.Arg
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import co.metalab.asyncawait.async
import pl.ownvision.scorekeeper.core.App
import pl.ownvision.scorekeeper.db.AppDatabase
import pl.ownvision.scorekeeper.views.activities.GameActivity
import javax.inject.Inject

/**
 * Created by jakub on 05.06.2017.
 */
open class BaseFragment : Fragment(), LifecycleRegistryOwner {

    @Inject lateinit protected var database: AppDatabase
    @Inject lateinit protected var application: App
    @Inject lateinit protected var viewModelFactory: ViewModelProvider.Factory

    // TODO : use complement base class after alpha stage for library
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        async.cancelAll()
    }

    protected fun getGameActivity(): GameActivity {
        if(activity !is GameActivity) throw RuntimeException("Bad GameActivity reference")
        return activity as GameActivity
    }

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry
}