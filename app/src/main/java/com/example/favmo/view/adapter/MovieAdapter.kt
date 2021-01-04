package com.example.favmo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favmo.R

import com.example.favmo.data.model.Movie
import com.example.favmo.view.CellClickListener
import com.example.favmo.view.FavoriteClickListner
import com.example.favmo.view.LoadMoreListener
import kotlinx.android.synthetic.main.loadmore.view.*
import kotlinx.android.synthetic.main.row.view.*


class MovieAdapter(
    private val movies: ArrayList<Movie>,
    private val cellClickListener: CellClickListener,
    private val favoriteClickListener: FavoriteClickListner,
    private val loadMoreListener: LoadMoreListener
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var page = 1
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.row -> (holder as MovieViewHolder).bind(movies[position])
            R.layout.loadmore -> (holder as ButtonViewHolder).bind()
        }
        if(position <= itemCount-2){
            val data = movies[position]
            holder.itemView.setOnClickListener {
                cellClickListener.onCellClickListener(data)
            }
            holder.itemView.favorite_btn.setOnClickListener{
                favoriteClickListener.onFavoriteClickListener(data)
            }
        }else{
            holder.itemView.loadmore.setOnClickListener{
                page+=1
                loadMoreListener.onLoadMoreListener(page)
                notifyDataSetChanged()
            }
        }

    }

    override fun getItemCount() = movies.size + 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val Itemview = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        val Buttonview = LayoutInflater.from(parent.context).inflate(R.layout.loadmore, parent, false)
        return when (viewType)
        {
            R.layout.row ->
            {
                MovieViewHolder(Itemview)
            }
            R.layout.loadmore->
            {
                ButtonViewHolder(Buttonview)
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
        //return MovieViewHolder(view)
    }

    class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener
    {
        private var view : View = itemView
        private lateinit var movie : Movie
        init {
            itemView.setOnClickListener(this)
            itemView.loadmore.setOnClickListener(this)
        }
        fun bind() {

        }
        override fun onClick(p0: View?) {
        }
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

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            movies.size -> R.layout.loadmore
            else -> R.layout.row
        }
    }





}