package com.vyakunin.testapp.interactor

import com.vyakunin.testapp.data.MovieEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface IDetailsInteractor {
    fun movieObserver(movieId: Long): Observable<MovieEntity>
    fun downloadMovieDetailsCompletable(movieId: Long): Completable
}