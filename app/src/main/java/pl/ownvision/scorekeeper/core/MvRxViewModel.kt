package pl.ownvision.scorekeeper.core

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.BuildConfig
import com.airbnb.mvrx.MvRxState
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class MvRxViewModel<S : MvRxState>(initialState: S) : BaseMvRxViewModel<S>(initialState, debugMode = BuildConfig.DEBUG) {

    val errorSubject : PublishSubject<Throwable> = PublishSubject.create()

    protected fun runActionInBackground(action: () -> Unit) :Disposable = Single.fromCallable(action)
            .doOnError { exception ->
                errorSubject.onNext(exception)
            }
            .subscribeOn(Schedulers.io())
            .subscribe()
}