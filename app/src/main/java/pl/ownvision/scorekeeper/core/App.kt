package pl.ownvision.scorekeeper.core

import android.app.Application
import com.crashlytics.android.BuildConfig
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.facebook.stetho.Stetho
import io.fabric.sdk.android.Fabric
import net.danlew.android.joda.JodaTimeAndroid



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
        val crashlyticsCore = CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()
        Fabric.with(this, Crashlytics.Builder().core(crashlyticsCore).build())
        Stetho.initializeWithDefaults(this)
        JodaTimeAndroid.init(this)
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