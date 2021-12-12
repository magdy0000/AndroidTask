package com.example.androidtask.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiClient @Inject constructor(private val apiService: ApiServices) {

    suspend fun getAllMovies() = withContext(Dispatchers.IO) { apiService.getMovies() }


    suspend fun getGenres() = withContext(Dispatchers.IO) { apiService.getGenre() }

}