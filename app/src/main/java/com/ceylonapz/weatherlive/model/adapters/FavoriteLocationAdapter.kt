package com.ceylonapz.weatherlive.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ceylonapz.weatherlive.databinding.ListItemFavoriteBinding
import com.ceylonapz.weatherlive.utilities.db.Favorite

class FavoriteLocationAdapter(private val onClick: (Favorite) -> Unit) :
    ListAdapter<Favorite, RecyclerView.ViewHolder>(FavoriteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteViewHolder(
            ListItemFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val favoriteLocation = getItem(position)
        holder.itemView.setOnClickListener { onClick(favoriteLocation) }
        (holder as FavoriteViewHolder).bind(favoriteLocation)
    }

    class FavoriteViewHolder(
        private val binding: ListItemFavoriteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fav: Favorite) {
            binding.apply {
                favorite = fav
                executePendingBindings()
            }
        }
    }
}

private class FavoriteDiffCallback : DiffUtil.ItemCallback<Favorite>() {

    override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldFavLocation: Favorite, newFevLocation: Favorite): Boolean {
        return oldFavLocation == newFevLocation
    }
}
