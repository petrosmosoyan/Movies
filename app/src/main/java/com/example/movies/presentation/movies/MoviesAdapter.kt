package com.example.movies.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.ItemMovieBinding
import com.example.movies.domain.model.Movie

class MoviesAdapter(
    val onItemClick: (Int) -> Unit,
    val onFavoriteClick: (Int) -> Unit
) : ListAdapter<Movie, MoviesAdapter.MyViewHolder>(MoviesDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): MyViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onItemClick, onFavoriteClick)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        p1: Int
    ) {
        holder.bind(getItem(p1))
    }

    class MyViewHolder(
        val binding: ItemMovieBinding,
        val onItemClick: (Int) -> Unit,
        val onFavoriteClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.run {
                title.text = item.title
                Glide.with(root.context).load(item.posterPath).into(poster)

                val favoriteIcon =
                    if (item.isFavorite) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite
                favorite.setImageResource(favoriteIcon)

                root.setOnClickListener {
                    onItemClick(item.id ?: return@setOnClickListener)
                }
                favorite.setOnClickListener {
                    onFavoriteClick(item.id ?: return@setOnClickListener)
                }
            }
        }
    }
}

class MoviesDiffUtil : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(p0: Movie, p1: Movie): Boolean = p0.id == p1.id

    override fun areContentsTheSame(p0: Movie, p1: Movie) = p0 == p1
}