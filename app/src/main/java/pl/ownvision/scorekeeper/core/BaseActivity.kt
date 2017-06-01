package pl.ownvision.scorekeeper.core

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm

/**
 * Created by jakub on 30.05.2017.
 */

open class BaseActivity : AppCompatActivity(){

    lateinit protected var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        realm = Realm.getDefaultInstance()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}