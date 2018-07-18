package com.vyakunin.testapp.repo

import android.util.Log
import com.vyakunin.testapp.api.MovieDTO
import com.vyakunin.testapp.data.MovieEntity
import com.vyakunin.testapp.data.MovieEntity_
import com.vyakunin.testapp.data.toRankedEntity
import com.vyakunin.testapp.data.updateDetails
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.rx.RxQuery
import io.reactivex.Observable

class MovieRepo(private val store: BoxStore) {

    private val moviesBox: Box<MovieEntity> by lazy { store.boxFor(MovieEntity::class.java) }

    fun deleteAll() = moviesBox.removeAll()

    fun updateDetails(dtoList: List<MovieDTO>) = store.runInTx {
        val locals: MutableList<MovieEntity> = moviesBox.all
        dtoList.forEach { dto ->
            locals.firstOrNull { it.id == dto.id.toLong() }
                    ?.apply {
                        moviesBox.put(dto.updateDetails(this))
                        locals.remove(this)
                    }
                    ?: run {
                        Log.w("MovieRepo", "Movie with id=${dto.id} is missing in the DB, details will not be added")
                    }
        }
    }

    fun insertUpdateRank(dtoList: List<MovieDTO>) = store.runInTx {
        val locals: MutableList<MovieEntity> = moviesBox.all
        dtoList.forEach { dto ->
            locals.firstOrNull { it.id == dto.id.toLong() }
                    ?.apply {
                        if (this.rank != dto.rank) {
                            this.rank = dto.rank
                            moviesBox.put(this)//update
                        }
                    }
                    ?: run {
                        moviesBox.put(dto.toRankedEntity())//insert
                    }
        }
    }

    fun getEmptyMovieIds(): IntArray {
        val query = moviesBox.query().isNull(MovieEntity_.description).build()
        return query.findIds().map { it.toInt() }.toIntArray()
    }

    fun getAll() = moviesBox.all

    fun moviesByRankObservable(): Observable<List<MovieEntity>> =
            RxQuery.observable(moviesBox.query().order(MovieEntity_.rank).build())

    fun movieObservableById(movieId: Long): Observable<MovieEntity> =
            RxQuery.observable(moviesBox.query().equal(MovieEntity_.id, movieId).build()).map { it.first() }
}