package com.vyakunin.testapp.data

import com.google.gson.Gson
import com.vyakunin.testapp.api.MovieDTO

//normally with DTO we should receive posterUrl
fun MovieDTO.toRankedEntity(): MovieEntity =
        MovieEntity(id = id.toLong(), rank = rank, name = name, posterUrl = "http://via.placeholder.com/350x500?id=$id")

fun MovieDTO.updateDetails(entity: MovieEntity): MovieEntity = entity.also {
    val gson = Gson()
    it.description = description
    it.director = director
    it.duration = duration
    if (actors?.isNotEmpty() == true) {
        it.actorsJsonArray = gson.toJson(actors)
    }
    if (genres?.isNotEmpty() == true) {
        it.genresJsonArray = gson.toJson(genres)
    }
}