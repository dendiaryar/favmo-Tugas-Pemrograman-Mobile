package com.example.favmo.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favmo.R
import com.example.favmo.data.model.Movie
import kotlinx.android.synthetic.main.favorite_and_wlist.view.*
import kotlinx.android.synthetic.main.row.view.*

class FavoriteAdapter() : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var listFavorites = ArrayList<Movie>()
        set(listFavorites) {
            if (listFavorites.size > 0) {
                this.listFavorites.clear()
            }
            this.listFavorites.addAll(listFavorites)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_and_wlist, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    override fun getItemCount(): Int = this.listFavorites.size

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView
        fun bind(note: Movie) {
                val imageUrl = StringBuilder()
                imageUrl.append(view.context.getString(R.string.base_path_poster))
                    .append(note.posterPath)
                view.mvftitle.setText(note.originalTitle)
                view.ratingf_value.setText(note.voteaverage.toString())
                view.popularityf_value.setText(note.popularity.toString())
                Glide.with(view.context).load(imageUrl.toString()).into(view.imagefTV)
        }
    }

    fun removeItem(position: Int) {
        this.listFavorites.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFavorites.size)
    }
}