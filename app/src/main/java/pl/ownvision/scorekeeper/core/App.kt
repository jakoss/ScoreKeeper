package pl.ownvision.scorekeeper.core

import android.app.Application
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by jakub on 30.05.2017.
 */

class App : Application(){

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        val logConfig = LogConfiguration.Builder()
                .logLevel(LogLevel.ALL)
                .t()
                .b()
                .st(5)
                .build()
        XLog.init(logConfig)
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}