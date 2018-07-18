package com.vyakunin.testapp.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieDTO(
        @SerializedName("Id")
        @Expose
        val id: Int,
        @SerializedName("Rank")
        @Expose
        val rank: Int = 0,
        @SerializedName("Name")
        @Expose
        val name: String,
        @SerializedName("Duration")
        @Expose
        val duration: String? = null,
        @SerializedName("Description")
        @Expose
        val description: String? = null,
        @SerializedName("Director")
        @Expose
        val director: String? = null,
        @SerializedName("Genres")
        @Expose
        val genres: List<String>? = null,
        @SerializedName("Actors")
        @Expose
        val actors: List<String>? = null)