package com.vyakunin.testapp.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class MovieEntity(
        @Id(assignable = true) var id: Long = 0,
        var name: String,
        var rank: Int,
        var duration: String? = null,
        var description: String? = null,
        var director: String? = null,
        var actorsJsonArray: String? = null, //serialized json array of actors
        var genresJsonArray: String? = null, //serialized json array of genres
        var posterUrl: String? = null)