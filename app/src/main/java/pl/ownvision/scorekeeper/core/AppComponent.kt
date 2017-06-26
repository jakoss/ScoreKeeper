package pl.ownvision.scorekeeper.core

import dagger.Component
import pl.ownvision.scorekeeper.views.activities.*
import pl.ownvision.scorekeeper.views.fragments.*
import javax.inject.Singleton

/**
 * Created by jakub on 30.05.2017.
 */

@Singleton
@Component(modules = arrayOf(
        AppModule::class
))
interface AppComponent {
    fun inject(activity: BaseActivity)

    fun inject(fragment: BaseFragment)
}