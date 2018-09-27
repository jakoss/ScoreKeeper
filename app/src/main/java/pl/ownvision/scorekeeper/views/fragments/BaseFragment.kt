package pl.ownvision.scorekeeper.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import co.metalab.asyncawait.async
import pl.ownvision.scorekeeper.core.App
import pl.ownvision.scorekeeper.db.AppDatabase
import pl.ownvision.scorekeeper.views.activities.GameActivity
import javax.inject.Inject

open class BaseFragment : androidx.fragment.app.Fragment() {

    @Inject lateinit protected var database: AppDatabase
    @Inject lateinit protected var application: App
    @Inject lateinit protected var viewModelFactory: ViewModelProvider.Factory

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
}