package com.example.androidtask.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.androidtask.data.local.MyDao
import com.example.androidtask.data.models.Genre
import com.example.androidtask.data.models.GenresModel
import com.example.androidtask.data.network.ApiClient
import com.example.androidtask.data.network.NetworkState
import com.example.androidtask.data.models.Movie
import com.example.androidtask.data.models.MovieModel
import com.example.androidtask.data.models.MoviesListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class Repo @Inject
constructor(
    private val apiClient: ApiClient, private val myDao: MyDao
) {

    private var  finalMovieList :ArrayList<MovieModel> = ArrayList()
    private var  movies: MoviesListModel?= null
    private var  genres: GenresModel?= null
    val newsLiveData = MutableLiveData<NetworkState>()



    private suspend fun getMoviesRemote() {

        CoroutineScope(Dispatchers.Default).launch {

            val moviesJob =  launch {
                movies = apiClient.getAllMovies()
            }
            val moviesGenres =  launch {
                genres = apiClient.getGenres()
            }
            moviesJob.join()
            moviesGenres.join()

            getMovieList(genres?.genres!!,movies?.results!!)
        }
    }
    private suspend fun getMovieList(list: ArrayList<Genre>
                                     , moviesList:ArrayList <Movie>)  {

        for (genre in list){
            val newList =   moviesList.filter {
                it.genre_ids.contains(genre.id)
            }
            if (newList.isNotEmpty())
            finalMovieList.add(MovieModel(1,genre.name,newList as ArrayList<Movie>))
        }
        newsLiveData.postValue( NetworkState.getLoaded(finalMovieList))
        myDao.insertMovies(finalMovieList)
    }

    suspend fun getMovies() {
        newsLiveData.postValue( NetworkState.LOADING)
        getDataFromCache()
        try {
            getMoviesRemote()
        } catch (e: Exception) {
            newsLiveData.postValue(  NetworkState.getErrorMessage(e))
        }
    }
    private suspend fun getDataFromCache (){
        try {
            val data  =  myDao.getAllMovies()
            if (data != null)
                newsLiveData.postValue(  NetworkState.getLoaded(data))
        }catch (e : Exception){
            newsLiveData.postValue(  NetworkState.getErrorMessage(e))
        }

    }


}