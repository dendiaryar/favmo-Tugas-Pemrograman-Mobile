package com.example.favmo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favmo.R
import com.example.favmo.data.model.Movie
import com.example.favmo.view.BrowseMovie
import com.example.favmo.view.CellClickListener
import com.example.favmo.view.FavoriteClickListner
import kotlinx.android.synthetic.main.row.view.*

class MovieAdapter(val movies : ArrayList<Movie>, private val cellClickListener: CellClickListener,private  val favoriteClickListener: FavoriteClickListner)
    : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies.get(position))
        val data = movies[position]
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(data)
        }
        holder.itemView.favorite_btn.setOnClickListener{
            favoriteClickListener.onFavoriteClickListener(data)
        }
    }

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MovieViewHolder(view)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var view : View = itemView
        private lateinit var movie : Movie

        override fun onClick(p0: View?) {
        }
        init {
            itemView.setOnClickListener(this)
            itemView.favorite_btn.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            this.movie = movie
            val imageUrl = StringBuilder()
            imageUrl.append(view.context.getString(R.string.base_path_poster)).append(movie.posterPath)
            view.mvtitle.setText(movie.originalTitle)
            view.rating_value.setText(movie.voteaverage.toString())
            view.popularity_value.setText(movie.popularity.toString())
            Glide.with(view.context).load(imageUrl.toString()).into(view.imageTV)
        }
    }

}