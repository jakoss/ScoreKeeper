package pl.ownvision.scorekeeper.core

import activitystarter.ActivityStarter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by jakub on 30.05.2017.
 */

open class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        ActivityStarter.fill(this)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}