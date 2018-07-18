package com.vyakunin.testapp.presentation.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.vyakunin.testapp.data.MovieEntity

interface MainView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToMovieDetails(id: Long)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showData(items: List<MovieEntity>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showDataNotAvailableMessage(connected: Boolean)
}