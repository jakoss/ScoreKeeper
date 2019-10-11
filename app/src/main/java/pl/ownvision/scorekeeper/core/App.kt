package pl.ownvision.scorekeeper.core

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.facebook.stetho.Stetho
import io.fabric.sdk.android.Fabric
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import pl.ownvision.scorekeeper.BuildConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val crashlyticsCore = CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()
        Fabric.with(this, Crashlytics.Builder().core(crashlyticsCore).build())
        Stetho.initializeWithDefaults(this)
        JodaTimeAndroid.init(this)

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(applicationModule)
        }

        val logConfig = LogConfiguration.Builder()
                .logLevel(LogLevel.ALL)
                .t()
                .b()
                .st(5)
                .build()
        XLog.init(logConfig)
    }
}