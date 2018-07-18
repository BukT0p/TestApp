package com.vyakunin.testapp.presentation.detailed

import com.arellomobile.mvp.InjectViewState
import com.vyakunin.testapp.interactor.IMainInteractor
import com.vyakunin.testapp.presentation.common.MvpRxPresenter
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class DetailedPresenter(
        var selectedPos: Int,
        val interactor: IMainInteractor,
        private val uiScheduler: Scheduler,
        private val bgScheduler: Scheduler) : MvpRxPresenter<DetailedView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        interactor.moviesObservable()
                .subscribeOn(bgScheduler)
                .map { it.map { it.id } }
                .observeOn(uiScheduler)
                .subscribeBy {
                    viewState.showMovieIds(it, selectedPos)
                }.addTo(subscriptions)
    }

    fun onPageSelected(position: Int) {
        selectedPos = position
    }
}