package com.example.movieapp.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.FragmentContainerView
import com.bumptech.glide.Glide
import com.example.movieapp.data.model.ModelResponse.Data
import com.example.movieapp.data.model.SearchMovieModelResponse.Data.Item
import com.example.movieapp.databinding.ItemMovieBinding

class SearchViewAdapter(
    private val context: Context,
    private var items: List<Item>,
    private val listener: OnItemClickListener
) : BaseAdapter() {

    interface OnItemClickListener {
        fun onItemClick(movie: Item)
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false)
        val item = items[position]
        val url = "https://phimimg.com/${item.poster_url}"

        binding.tvMoviename.text = item.name
        Glide.with(context).load(url).into(binding.imageView)


        binding.root.setOnClickListener {
            listener.onItemClick(item)
        }

        return binding.root
    }

    fun updateMovies(newMovies: List<Item>) {
        items = newMovies
        notifyDataSetChanged()
    }
}
