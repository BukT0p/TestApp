package com.vyakunin.testapp.api

import retrofit2.Call

class MovieApiWrapper(private val api: IMovieApi,
                      private val authToken: String) {

    fun allMovies(): Call<List<MovieDTO>> =
            api.allMovies(authToken = authToken)

    fun moviesByRank(startRankIndex: Int, numMovies: Int): Call<List<MovieDTO>> =
            api.moviesByRank(authToken = authToken, startRankIndex = startRankIndex, numMovies = numMovies)

    fun moviesDetails(movieIds: IntArray): Call<List<MovieDTO>> =
            api.movieDetails(authToken = authToken, movieIds = movieIds)
}