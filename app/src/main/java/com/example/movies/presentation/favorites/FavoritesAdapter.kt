package com.example.movies.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.ItemFavoriteBinding
import com.example.movies.domain.model.Favorite

class FavoritesAdapter : ListAdapter<Favorite, FavoritesAdapter.MyViewHolder>(FavoriteDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): MyViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MyViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Favorite) {
            binding.run {
                title.text = item.title
                Glide.with(root.context).load(item.posterPath).into(poster)
            }
        }
    }
}

class FavoriteDiffUtil : DiffUtil.ItemCallback<Favorite>() {
    override fun areItemsTheSame(p0: Favorite, p1: Favorite) = p0.id == p1.id

    override fun areContentsTheSame(p0: Favorite, p1: Favorite) = p0 == p1
}