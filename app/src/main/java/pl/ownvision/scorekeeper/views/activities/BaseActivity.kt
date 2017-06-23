package pl.ownvision.scorekeeper.views.activities

import activitystarter.ActivityStarter
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.App
import pl.ownvision.scorekeeper.db.AppDatabase
import pl.ownvision.scorekeeper.repositories.GameRepository
import javax.inject.Inject

/**
 * Created by jakub on 30.05.2017.
 */

open class BaseActivity : AppCompatActivity(), LifecycleRegistryOwner {

    @Inject lateinit protected var database: AppDatabase
    @Inject lateinit protected var application: App

    // TODO : use complement base class after alpha stage for library
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        ActivityStarter.fill(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        ActivityStarter.save(this, outState)
    }

    fun setToolbar(parentView: View){
        val toolbarView = parentView.findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbarView)
    }

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry
}