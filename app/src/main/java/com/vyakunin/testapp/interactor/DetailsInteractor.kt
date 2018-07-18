package com.vyakunin.testapp.interactor

import com.vyakunin.testapp.api.MovieApiWrapper
import com.vyakunin.testapp.data.MovieEntity
import com.vyakunin.testapp.repo.MovieRepo
import io.reactivex.Completable
import io.reactivex.Observable

class DetailsInteractor(val repo: MovieRepo,
                        val api: MovieApiWrapper) : IDetailsInteractor {

    override fun downloadMovieDetailsCompletable(movieId: Long): Completable = Completable.create {
        val call = api.moviesDetails(intArrayOf(movieId.toInt()))
        val response = call.execute()
        val dtoList = response.body()
        if (response.isSuccessful && dtoList != null) {
            repo.updateDetails(dtoList)
        }
        it.onComplete()
    }

    override fun movieObserver(movieId: Long): Observable<MovieEntity> = repo.movieObservableById(movieId)

}