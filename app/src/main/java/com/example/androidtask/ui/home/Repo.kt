package com.example.androidtask.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.androidtask.data.local.MyDao
import com.example.androidtask.data.models.Genre
import com.example.androidtask.data.models.GenresModel
import com.example.androidtask.data.network.ApiClient
import com.example.androidtask.data.network.NetworkState
import com.example.androidtask.data.models.Movie
import com.example.androidtask.data.models.MovieModel
import com.example.androidtask.data.models.MoviesListModel
import com.example.androidtask.utils.MySharedPreferences
import com.example.androidtask.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Repo @Inject
constructor(
    private val apiClient: ApiClient, private val myDao: MyDao
) {

    private var finalMovieList: ArrayList<MovieModel> = ArrayList()
    private var movies: MoviesListModel? = null
    private var genres: GenresModel? = null
    val newsLiveData = SingleLiveEvent<NetworkState>()


    private suspend fun getMoviesRemote() {
        newsLiveData.postValue(NetworkState.LOADING)
        CoroutineScope(Dispatchers.Default).launch {
                val moviesJob = launch {
                    try {
                        movies = apiClient.getAllMovies()
                    }catch (e:Exception){
                        Log.e("TAG", "getMoviesRemote: ",)
                        getDataFromCache()
                        newsLiveData.postValue(NetworkState.getErrorMessage(e))
                    }
                }
                moviesJob.join()
                val moviesGenres = launch {
                    try {
                        genres = apiClient.getGenres()
                    }catch (e : Exception){
                        newsLiveData.postValue(NetworkState.getErrorMessage(e))
                        Log.e("TAG", "getMoviesRemote: ",)
                    }
                }
               moviesGenres.join()
            if (genres!= null)
            getMovieList(genres?.genres!!, movies?.results!!)
        }
    }

    private suspend fun getMovieList(
        list: ArrayList<Genre>, moviesList: ArrayList<Movie>
    ) {

        for (genre in list) {
            val newList = moviesList.filter {
                it.genre_ids.contains(genre.id)
            }
            if (newList.isNotEmpty())
                finalMovieList.add(
                    MovieModel(
                        genreName = genre.name,
                        moviesList = newList as ArrayList<Movie>
                    )
                )
        }
        newsLiveData.postValue(NetworkState.getLoaded(finalMovieList))
        myDao.insertMovies(finalMovieList)
        MySharedPreferences.setTime(System.currentTimeMillis())
    }

    suspend fun getMovies() {
        val time = System.currentTimeMillis() - MySharedPreferences.getTime()
        val timeDeff = (time / (1000 * 60 * 60)).toInt()


        if (timeDeff >= 4) {
            getMoviesRemote()
        } else {
            getDataFromCache()

        }

    }


    private suspend fun getDataFromCache() {

        try {
            val data = myDao.getAllMovies()
            if (data != null)
                newsLiveData.postValue(NetworkState.getLoaded(data))
        }catch (e : Exception){

            Log.e("TAG", "getDataFromCache:$e ", )
        }



    }


}