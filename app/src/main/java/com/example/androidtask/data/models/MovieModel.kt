package com.example.androidtask.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.androidtask.data.local.Converters

@Entity
data class MovieModel
    (
    @PrimaryKey(autoGenerate = true)
    val id: Int ?= null ,
    val genreName: String,
    @TypeConverters(Converters::class)
    val moviesList: ArrayList<Movie>
)
