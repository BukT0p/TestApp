package com.vyakunin.testapp.presentation.detailed

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface DetailedView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showMovieIds(movieIds: List<Long>, selectedPos: Int)
}