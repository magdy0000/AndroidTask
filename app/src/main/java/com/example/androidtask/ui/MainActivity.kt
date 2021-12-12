package com.example.androidtask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidtask.R
import com.example.androidtask.utils.ProgressLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onDestroy() {
        super.onDestroy()
        ProgressLoading.dismiss()
    }
}