package com.example.androidtask.utils

import android.content.Context
import android.content.SharedPreferences

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object MySharedPreferences {


    private var mAppContext: Context? = null
    private const val SHARED_PREFERENCES_NAME = "b2b data"
    private const val TIME = "time"


    fun init(appContext: Context?) {
        mAppContext = appContext
    }

    private fun getSharedPreferences(): SharedPreferences {
        return mAppContext!!.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun setTime(timeGotData: Long) {

        val editor = getSharedPreferences().edit()
        editor.putLong(TIME, timeGotData).apply()
    }

    fun getTime(): Long {
        return getSharedPreferences().getLong(TIME , 0)
    }



}