package com.vyakunin.testapp.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.vyakunin.testapp.data.MovieEntity
import com.vyakunin.testapp.interactor.IMainInteractor
import com.vyakunin.testapp.presentation.common.MvpRxPresenter
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class MainPresenter(
        private val interactor: IMainInteractor,
        private val uiScheduler: Scheduler,
        private val bgScheduler: Scheduler) : MvpRxPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        interactor.moviesObservable()
                .subscribeOn(bgScheduler)
                .observeOn(uiScheduler)
                .subscribeBy {
                    if (it.isEmpty()) {
                        viewState.showDataNotAvailableMessage(interactor.isConnected())
                    } else {
                        viewState.showData(it)
                    }
                }.addTo(subscriptions)
    }

    fun onItemClicked(movie: MovieEntity) {
        viewState.navigateToMovieDetails(movie.id)
    }
}