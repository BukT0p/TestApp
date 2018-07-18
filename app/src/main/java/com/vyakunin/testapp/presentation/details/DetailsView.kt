package com.vyakunin.testapp.presentation.details

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface DetailsView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToTicketsBooking()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showHeader(rank: Int, posterUrl: String? = null, name: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showDetails(description: String? = null, duration: String? = null, director: String? = null, actors: String? = null, genres: String? = null)
}