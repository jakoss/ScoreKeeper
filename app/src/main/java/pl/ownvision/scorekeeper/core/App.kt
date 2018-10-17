package pl.ownvision.scorekeeper.core

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.facebook.stetho.Stetho
import io.fabric.sdk.android.Fabric
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.module
import pl.ownvision.scorekeeper.BuildConfig
import pl.ownvision.scorekeeper.db.AppDatabase

class App : Application(){

    private val applicationModule = module {
        val appDatabase = Room.databaseBuilder(this@App, AppDatabase::class.java, AppDatabase.DATABASE_NAME).build()
        single { appDatabase }
    }

    override fun onCreate() {
        super.onCreate()
        val crashlyticsCore = CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()
        Fabric.with(this, Crashlytics.Builder().core(crashlyticsCore).build())
        Stetho.initializeWithDefaults(this)
        JodaTimeAndroid.init(this)

        startKoin(this, listOf(applicationModule))

        val logConfig = LogConfiguration.Builder()
                .logLevel(LogLevel.ALL)
                .t()
                .b()
                .st(5)
                .build()
        XLog.init(logConfig)
    }
}