package pl.ownvision.scorekeeper.views.activities

import activitystarter.ActivityStarter
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import co.metalab.asyncawait.async
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.App
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject lateinit protected var application: App
    @Inject lateinit protected var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        ActivityStarter.fill(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        async.cancelAll()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        ActivityStarter.save(this, outState)
    }

    fun setToolbar(parentView: View){
        val toolbarView = parentView.findViewById<View>(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbarView)
    }
}