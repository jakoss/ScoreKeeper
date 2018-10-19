package pl.ownvision.scorekeeper.core

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import com.airbnb.mvrx.BaseMvRxFragment
import com.crashlytics.android.Crashlytics
import com.elvishew.xlog.XLog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pl.ownvision.scorekeeper.R

abstract class BaseFragment : BaseMvRxFragment() {

    lateinit var fabButton: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabButton = requireActivity().findViewById(R.id.fab)
    }

    protected fun handleError(throwable: Throwable, @StringRes message: Int) {
        requireActivity().alert(message)
        XLog.e("Error", throwable)
        Crashlytics.logException(throwable)
    }

    protected fun showFabButton(action: () -> Unit) {
        fabButton.show()
        fabButton.setOnClickListener { action() }
    }

    protected fun hideFabButton() {
        fabButton.hide()
    }
}