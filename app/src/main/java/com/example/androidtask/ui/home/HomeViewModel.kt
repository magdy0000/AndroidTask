package com.example.androidtask.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtask.data.network.NetworkState
import com.example.androidtask.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject constructor(private val repo: Repo) : ViewModel() {


    val moviesLiveData: SingleLiveEvent<NetworkState> = repo.newsLiveData

    fun getAllMovies() {

        viewModelScope.launch {
            repo.getMovies()
        }
    }

}