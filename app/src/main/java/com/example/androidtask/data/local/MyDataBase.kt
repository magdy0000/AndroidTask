package com.example.androidtask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidtask.data.models.MovieModel

@Database(entities = arrayOf(MovieModel::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class  MyDataBase  : RoomDatabase() {
    abstract fun getMyDao(): MyDao


}