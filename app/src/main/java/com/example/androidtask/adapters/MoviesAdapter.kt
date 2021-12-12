package com.example.androidtask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidtask.R
import com.example.androidtask.data.models.Movie
import com.example.androidtask.databinding.ItemMovieBinding
import com.example.androidtask.utils.Const

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.Holder>() {

    var list : ArrayList<Movie> ?= null

    var onItemClick : OnItemClick ?= null

    fun setData (list: ArrayList<Movie>){

        this.list = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val data = list?.get(position)

        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(Const.BASE_IMAGE+data?.poster_path)
                .placeholder(R.drawable.ic_place_holder)
                .into(imageMovie)
            textTitle.text = data?.original_title
        }




    }

    override fun getItemCount(): Int {
        return   if (list == null) 0 else list?.size!!
    }


    inner class Holder (val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){


        init {

            itemView.setOnClickListener {
                onItemClick?.onClick(list?.get(layoutPosition)!!)
            }
        }

    }

    interface OnItemClick {

        fun onClick (movie: Movie)
    }
}