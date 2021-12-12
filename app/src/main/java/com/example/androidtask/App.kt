package com.example.androidtask

import android.app.Application
import com.example.androidtask.utils.MySharedPreferences
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MySharedPreferences.init(this)
    }
}