package com.example.androidtask.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.androidtask.data.models.Movie
import java.lang.reflect.Type


class Converters {

    @TypeConverter
    fun fromString(value: String?):ArrayList<Movie>  {
        val listType: Type = object : TypeToken<List<Movie>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Movie>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}