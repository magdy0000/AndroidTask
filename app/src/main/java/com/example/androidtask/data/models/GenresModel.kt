package com.example.androidtask.data.models

data class GenresModel(
    val genres: ArrayList<Genre>
)

data class Genre(
    val id: Int,
    val name: String
)