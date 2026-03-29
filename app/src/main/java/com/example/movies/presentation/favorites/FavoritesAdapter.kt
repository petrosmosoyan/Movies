package com.example.movies.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.ItemFavoriteBinding
import com.example.movies.domain.model.Favorite
import java.util.Locale

class FavoritesAdapter(val onItemClick: (Int) -> Unit, val onDeleteClick: (Int) -> Unit) :
    ListAdapter<Favorite, FavoritesAdapter.MyViewHolder>(FavoriteDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): MyViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onItemClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MyViewHolder(
        val binding: ItemFavoriteBinding,
        val onItemClick: (Int) -> Unit,
        val onDeleteClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Favorite) {
            binding.run {
                val ratingValue = item.voteAverage ?: 0.0
                val voteAverage = "★ ${String.format(Locale.US, "%.1f", ratingValue)} / 10"

                title.text = item.title
                rating.text = voteAverage
                releaseDate.text = item.releaseDate
                Glide.with(root.context).load(item.posterPath).into(poster)

                root.setOnClickListener {
                    onItemClick(item.id)
                }

                deleteFavorite.setOnClickListener {
                    onDeleteClick(item.id)
                }
            }
        }
    }
}

class FavoriteDiffUtil : DiffUtil.ItemCallback<Favorite>() {
    override fun areItemsTheSame(p0: Favorite, p1: Favorite) = p0.id == p1.id

    override fun areContentsTheSame(p0: Favorite, p1: Favorite) = p0 == p1
}