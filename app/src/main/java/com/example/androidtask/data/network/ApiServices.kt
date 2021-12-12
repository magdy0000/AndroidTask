package com.example.androidtask.data.network

import com.example.androidtask.data.models.GenresModel
import com.example.androidtask.data.models.MoviesListModel
import retrofit2.http.GET

interface ApiServices {



    @GET("discover/movie")
    suspend fun getMovies() : MoviesListModel


    @GET("genre/movie/list")
    suspend fun getGenre () : GenresModel
}