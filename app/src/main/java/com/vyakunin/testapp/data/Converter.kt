package com.vyakunin.testapp.data

import com.vyakunin.testapp.api.MovieDTO


//normally with DTO we should receive posterUrl
fun MovieDTO.toRankedEntity(): MovieEntity =
        MovieEntity(id = id.toLong(), rank = rank, name = name, posterUrl = "http://via.placeholder.com/250x300?id=$id")

fun MovieDTO.updateDetails(entity: MovieEntity): MovieEntity = entity.also {
    it.description = description
    it.director = director
    it.duration = duration
    if (actors?.isNotEmpty() == true) {
        it.actorsList = actors.joinToString()
    }
    if (genres?.isNotEmpty() == true) {
        it.genresList = genres.joinToString()
    }
}