package com.vyakunin.testapp.interactor

import com.vyakunin.testapp.data.MovieEntity
import com.vyakunin.testapp.repo.MovieRepo
import io.reactivex.Observable

class MainInteractor(private val repo: MovieRepo) : IMainInteractor {

    override fun moviesObservable(): Observable<List<MovieEntity>> =
        repo.moviesByRankObservable()
}