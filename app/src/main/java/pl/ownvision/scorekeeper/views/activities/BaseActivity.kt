package pl.ownvision.scorekeeper.views.activities

import activitystarter.ActivityStarter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import pl.ownvision.scorekeeper.R
import pl.ownvision.scorekeeper.core.App
import pl.ownvision.scorekeeper.repositories.GameRepository
import javax.inject.Inject

/**
 * Created by jakub on 30.05.2017.
 */

open class BaseActivity : AppCompatActivity(){

    @Inject lateinit protected var gameRepository: GameRepository
    @Inject lateinit protected var application: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        ActivityStarter.fill(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        ActivityStarter.save(this, outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        gameRepository.closeRealm()
    }

    fun setToolbar(parentView: View){
        val toolbarView = parentView.findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbarView)
    }
}