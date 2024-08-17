package com.example.movieapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ItemSeriesMovieBinding
import com.example.movieapp.data.model.DetailModelResponse.Episode.ServerData

class SeriesRecyclerViewAdapter(
    private val episodes: List<ServerData>, private val onEpisodeClick: (ServerData) -> Unit
) : RecyclerView.Adapter<SeriesRecyclerViewAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = ItemSeriesMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = episodes[position]
        holder.bind(episode)
    }

    override fun getItemCount() = episodes.size

    inner class EpisodeViewHolder(private val binding: ItemSeriesMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: ServerData) {
            binding.textSeries.text = episode.name
            binding.btnPlay.setOnClickListener {
                onEpisodeClick(episode)
            }
        }
    }
}

