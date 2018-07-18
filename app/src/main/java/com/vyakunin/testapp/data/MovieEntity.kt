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
        var actorsList: String? = null, //list of actors separated with ", "
        var genresList: String? = null, //list of genres separated with ", "
        var posterUrl: String? = null)