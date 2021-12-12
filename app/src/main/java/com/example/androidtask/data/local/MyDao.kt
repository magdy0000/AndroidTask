package com.example.androidtask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidtask.data.models.MovieModel


@Dao
interface MyDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(model : ArrayList<MovieModel>)



    @Query("select * from MovieModel")
    suspend fun getAllMovies () : List<MovieModel>?


}