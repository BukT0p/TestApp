package com.vyakunin.testapp.presentation.details

import com.arellomobile.mvp.InjectViewState
import com.google.gson.Gson
import com.vyakunin.testapp.interactor.IDetailsInteractor
import com.vyakunin.testapp.presentation.common.MvpRxPresenter
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class DetailsPresenter(private val interactor: IDetailsInteractor,
                       private val uiScheduler: Scheduler,
                       private val bgScheduler: Scheduler,
                       private val movieId: Long) : MvpRxPresenter<DetailsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        interactor.movieObserver(movieId)
                .subscribeOn(bgScheduler)
                .observeOn(uiScheduler)
                .subscribeBy {
                    viewState.showHeader(it.rank, it.posterUrl, it.name)
                    if (it.description == null) {
                        viewState.showDetails()
                        viewState.showProgress(true)
                        downloadDetails()
                    } else {
                        viewState.showDetails(it.description, it.duration, it.director, it.actorsList, it.genresList)
                        viewState.showProgress(false)
                    }
                }.addTo(subscriptions)
    }

    fun onBookClicked() {
        viewState.navigateToTicketsBooking()
    }

    private fun downloadDetails() {

    }
}