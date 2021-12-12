package com.example.androidtask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.data.models.MovieModel
import com.example.androidtask.databinding.ItemAllMoviesBinding

class HomeRecyclerAdapter : RecyclerView.Adapter<HomeRecyclerAdapter.Holder>() {

    var listAllMovies : ArrayList<MovieModel>?= null

    var onItemClick : MoviesAdapter.OnItemClick?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       val binding = ItemAllMoviesBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = listAllMovies?.get(position)

       holder.binding.apply {
           textTitle.text = data?.genreName
           holder.adapter?.setData(data?.moviesList!!)
       }
    }

    override fun getItemCount(): Int {
      return  listAllMovies?.size ?: 0
    }

    inner class Holder(val binding: ItemAllMoviesBinding) : RecyclerView.ViewHolder(binding.root){
        var adapter: MoviesAdapter? = null
        init {

            adapter = MoviesAdapter()
            adapter?.onItemClick = onItemClick
            binding.recyclerMovies.setHasFixedSize(true)
            binding.recyclerMovies.adapter = adapter

        }
    }


}