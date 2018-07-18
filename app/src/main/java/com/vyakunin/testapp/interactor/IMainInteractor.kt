package com.vyakunin.testapp.interactor

import com.vyakunin.testapp.data.MovieEntity
import io.reactivex.Observable

interface IMainInteractor {
    fun moviesObservable(): Observable<List<MovieEntity>>
}