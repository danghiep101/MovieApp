package com.example.movieapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.data.model.ModelResponse.Data.Item
import com.example.movieapp.databinding.ItemMovieBinding

class MoviesRecyclerViewAdapter(
    private val movieList: MutableList<Item>, private val listener: OnItemClickListener
) : RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(movie: Item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
        holder.itemView.setOnClickListener {
            listener.onItemClick(movieList[position])
        }
    }

    class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            val url = "https://phimimg.com/${item.poster_url}"
            binding.tvMoviename.text = item.name
            Glide.with(binding.root.context).load(url).into(binding.imageView)
        }
    }

    fun addMovies(newMovies: List<Item>) {
        val startPosition = movieList.size
        movieList.addAll(newMovies)
        notifyItemRangeInserted(startPosition, newMovies.size)
    }
}
