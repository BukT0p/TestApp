package com.vyakunin.testapp

import com.vyakunin.testapp.api.MovieDTO
import com.vyakunin.testapp.repo.MovieRepo
import org.junit.Test
import kotlin.test.assertEquals

class MoviesRepoTest : AbsObTest() {
    @Test
    fun insertRankedPositive() {
        //given
        val repo = MovieRepo(store!!)
        val dtoList = listOf(MovieDTO(id = 1, rank = 1, name = "1"),
                MovieDTO(id = 2, rank = 2, name = "2"),
                MovieDTO(id = 3, rank = 3, name = "3"))
        //when
        repo.insertUpdateRank(dtoList)
        val emptyMovieIds = repo.getEmptyMovieIds()
        //then
        assertEquals(dtoList.size, emptyMovieIds.size)
    }

    @Test
    fun updateWithDetailsNegative() {
        //given
        val repo = MovieRepo(store!!)
        val dtoList = listOf(MovieDTO(id = 1, name = "1", description = "first"),
                MovieDTO(id = 2, name = "2", description = "second"),
                MovieDTO(id = 3, name = "3", description = "third"))
        //when
        repo.updateDetails(dtoList)
        //then
        assertEquals(0, repo.getAll().size)//nothing inserted
    }

    @Test
    fun updateWithDetailsPositive() {
        //given
        val repo = MovieRepo(store!!)
        val rankDTOList = listOf(MovieDTO(id = 1, rank = 1, name = "1"),
                MovieDTO(id = 2, rank = 2, name = "2"),
                MovieDTO(id = 3, rank = 3, name = "3"))
        val detailsDtoList = listOf(MovieDTO(id = 1, name = "1", description = "first"),
                MovieDTO(id = 2, name = "2", description = "second"))
        //when
        repo.insertUpdateRank(rankDTOList)
        repo.updateDetails(detailsDtoList)
        val emptyMovieIds = repo.getEmptyMovieIds()
        //then
        assertEquals(1, emptyMovieIds.size)//third still doesn't have details
        assertEquals(rankDTOList.size, repo.getAll().size)
    }
}