package com.vyakunin.testapp

import com.vyakunin.testapp.api.MovieApiWrapper
import com.vyakunin.testapp.api.MovieDTO
import com.vyakunin.testapp.app.appModule
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ApiWrapperTest : KoinTest {
    private val api: MovieApiWrapper by inject()

    @Before
    fun before() {
        startKoin(listOf(appModule))
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun allMoviesPositive() {
        val call = api.allMovies()
        val response = call.execute()
        assertTrue(response.isSuccessful)
        val movieList = response.body()
        assertNotNull(movieList)
        assertTrue(movieList!!.isNotEmpty())
        movieList.forEach { it.validateMetadata() }
    }

    @Test
    fun moviesByRankPositive() {
        val call = api.moviesByRank(1, 10)
        val response = call.execute()
        assertTrue(response.isSuccessful)
        val movieList = response.body()
        assertNotNull(movieList)
        assertTrue(movieList!!.isNotEmpty())
        assertEquals(10, movieList.size)
        movieList.forEach { it.validateRanked() }
    }

    @Test
    fun moviesDetailsPositive() {
        val call = api.moviesDetails(intArrayOf(283, 262))
        val response = call.execute()
        assertTrue(response.isSuccessful)
        val movieList = response.body()
        assertNotNull(movieList)
        assertTrue(movieList!!.isNotEmpty())
        assertEquals(2, movieList.size)
        movieList.forEach { it.validateFull() }
    }
}

private fun MovieDTO.validateRanked() {
    assertTrue(id > 0)
    assertTrue(rank > 0)
    assertTrue(name.isNotBlank())
}

private fun MovieDTO.validateMetadata() {
    assertTrue(id > 0)
    assertTrue(rank > 0)
    assertTrue(name.isNotBlank())
    assertFalse(duration.isNullOrBlank())
    assertFalse(director.isNullOrBlank())
    assertFalse(actors == null)
    assertFalse(genres == null)
}

private fun MovieDTO.validateFull() {
    assertTrue(id > 0)
    assertEquals(0, rank)
    assertTrue(name.isNotBlank())
    assertFalse(duration.isNullOrBlank())
    assertFalse(director.isNullOrBlank())
    assertFalse(description.isNullOrBlank())
    assertFalse(actors == null)
    assertFalse(genres == null)
}