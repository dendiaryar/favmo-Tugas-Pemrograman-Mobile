package com.example.favmo.view.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.favmo.R
import com.example.favmo.data.model.Movie
import kotlinx.android.synthetic.main.activity_single_movie.*
import kotlinx.android.synthetic.main.row.view.*

class SingleMovieActivity : AppCompatActivity() {
    lateinit var movie:Movie
    override fun onCreate(savedInstanceState: Bundle?) {
        movie = intent.getParcelableExtra<Movie>("movie") as Movie

        Toast.makeText(this,"ini diaktivi 2  ${movie.originalTitle}", Toast.LENGTH_LONG).show()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)
        if(movie!=null){
            val imageUrl = StringBuilder()
            imageUrl.append(getString(R.string.base_path_poster)).append(movie.posterPath)
            mvtitle.setText(movie.originalTitle)
            rating_value.setText(movie?.voteaverage.toString())
            popularity_value.setText(movie?.popularity.toString())
            Glide.with(this).load(imageUrl.toString()).into(imageTV)
            overview.setText(movie.overview)
        }

    }
}
