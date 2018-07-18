package com.vyakunin.testapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface description
 */
interface IMovieApi {

    /**
     * Retrieve the movie set
     * @param authToken - Required. A unique key that allows you to retrieve data.
     */
    @GET("AllMovies")
    fun allMovies(@Query("authToken") authToken: String): Call<List<MovieDTO>>

    /**
     * Retrieve the basic movie information based on rank
     * @param authToken - Required. A unique key that allows you to retrieve data.
     * @param startRankIndex - Required. The rank of the first movie you're interested in retrieving. The movies retrieved will start at this rank. The first possible index is 1 (ie. the top movie is assigned rank 1).
     * @param numMovies - Required. The total number of movies to be retrieved. Movies ranked from startRankIndex to startRankIndex + numMovies will be retrieved.
     */
    @GET("MoviesByRank")
    fun moviesByRank(@Query("authToken") authToken: String,
                     @Query("startRankIndex") startRankIndex: Int,
                     @Query("numMovies") numMovies: Int): Call<List<MovieDTO>>

    /**
     * Retrieve detailed movie information
     * @param authToken - Required. A unique key that allows you to retrieve data.
     * @param movieIds - Required. The ids of the movies to be retrieved. (e.g. [1, 2, 3]
     */
    @GET("MovieDetails")
    fun movieDetails(@Query("authToken") authToken: String,
                     @Query("movieIds") movieIds: IntArray): Call<List<MovieDTO>>
}