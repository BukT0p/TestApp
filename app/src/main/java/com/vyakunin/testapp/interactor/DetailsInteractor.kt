package com.vyakunin.testapp.interactor

import com.vyakunin.testapp.data.MovieEntity
import com.vyakunin.testapp.repo.MovieRepo
import io.reactivex.Observable

class DetailsInteractor(val repo: MovieRepo) : IDetailsInteractor {

    override fun movieObserver(movieId: Long): Observable<MovieEntity> = repo.movieObservableById(movieId)

}