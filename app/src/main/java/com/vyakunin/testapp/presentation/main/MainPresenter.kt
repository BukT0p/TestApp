package com.vyakunin.testapp.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.vyakunin.testapp.data.MovieEntity
import com.vyakunin.testapp.interactor.IMainInteractor
import com.vyakunin.testapp.presentation.common.MvpRxPresenter
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

@InjectViewState
class MainPresenter(private val interactor: IMainInteractor,
                    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
                    private val bgScheduler: Scheduler = Schedulers.io()) : MvpRxPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        interactor.moviesObservable()
                .subscribeOn(bgScheduler)
                .observeOn(uiScheduler)
                .subscribeBy {
                    viewState.showData(it)
                }.addTo(subscriptions)
    }

    fun onItemClicked(movie: MovieEntity) {
        viewState.navigateToMovieDetails(movie.id)
    }
}