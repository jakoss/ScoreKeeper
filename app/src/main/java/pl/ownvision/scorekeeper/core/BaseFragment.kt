package pl.ownvision.scorekeeper.core

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.MvRx
import kotlinx.android.synthetic.main.fragment_base_mvrx.*
import kotlinx.android.synthetic.main.fragment_base_mvrx.view.*
import androidx.navigation.fragment.findNavController
import com.crashlytics.android.Crashlytics
import com.elvishew.xlog.XLog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import pl.ownvision.scorekeeper.R

abstract class BaseFragment : BaseMvRxFragment() {

    protected val epoxyController by lazy { epoxyController() }

    protected lateinit var fab: FloatingActionButton

    private var errorHandler: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base_mvrx, container, false).apply {
            recycler_view.setController(epoxyController)
            this@BaseFragment.fab = findViewById(R.id.fab)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        errorHandler?.dispose()
    }

    override fun invalidate() {
        recycler_view.requestModelBuild()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        super.onDestroyView()
    }

    protected fun setupErrorHandler(observable: Observable<Throwable>) {
        errorHandler = observable.subscribe { exception ->
            this.alert(exception.localizedMessage)
            XLog.e("Error", exception)
            Crashlytics.logException(exception)
        }
    }

    protected fun navigateTo(@IdRes actionId: Int, arg: Parcelable? = null) {
        val bundle = arg?.let { Bundle().apply { putParcelable(MvRx.KEY_ARG, it) } }
        findNavController().navigate(actionId, bundle)
    }

    abstract fun epoxyController(): MvRxEpoxyController
}