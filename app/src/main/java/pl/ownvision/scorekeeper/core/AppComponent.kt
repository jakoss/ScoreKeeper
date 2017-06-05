package pl.ownvision.scorekeeper.core

import dagger.Component
import pl.ownvision.scorekeeper.views.activities.BaseActivity
import pl.ownvision.scorekeeper.views.activities.GameActivity
import pl.ownvision.scorekeeper.views.activities.MainActivity
import pl.ownvision.scorekeeper.views.fragments.BaseFragment
import pl.ownvision.scorekeeper.views.fragments.RoundsFragment
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
    fun inject(activity: MainActivity)
    fun inject(activity: GameActivity)

    fun inject(fragment: BaseFragment)
}