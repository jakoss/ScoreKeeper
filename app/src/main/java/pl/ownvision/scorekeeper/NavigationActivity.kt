package pl.ownvision.scorekeeper

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.airbnb.mvrx.BaseMvRxActivity
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : BaseMvRxActivity() {

    private val navController: NavController
        get() = findNavController(R.id.my_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)

        navController.addOnNavigatedListener { _, destination ->
            if (destination.isMainView()) {
                toolbar.navigationIcon = null
            }else{
                toolbar.setNavigationIcon(R.drawable.ic_back)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId ?: 0) {
        android.R.id.home -> {
            if (navController.currentDestination?.isMainView() != true) {
                navController.navigateUp()
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun NavDestination.isMainView() = id == R.id.gamesFragment
}