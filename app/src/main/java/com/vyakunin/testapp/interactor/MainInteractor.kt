package com.vyakunin.testapp.interactor

import android.content.Context
import android.net.ConnectivityManager
import com.vyakunin.testapp.app.App
import com.vyakunin.testapp.data.MovieEntity
import com.vyakunin.testapp.repo.MovieRepo
import io.reactivex.Observable

class MainInteractor(private val repo: MovieRepo,
                     private val app: App) : IMainInteractor {

    override fun isConnected(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnected == true
    }

    override fun moviesObservable(): Observable<List<MovieEntity>> =
            repo.moviesByRankObservable()
}