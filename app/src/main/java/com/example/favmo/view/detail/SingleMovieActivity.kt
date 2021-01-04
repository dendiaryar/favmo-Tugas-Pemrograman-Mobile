package com.example.favmo.view.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favmo.R
import com.example.favmo.data.model.Movie
import com.example.favmo.data.model.MovieResponse
import com.example.favmo.data.model.Review
import com.example.favmo.data.model.ReviewResponse
import com.example.favmo.data.service.ApiClient
import com.example.favmo.data.service.ApiInterface
import com.example.favmo.view.adapter.MovieAdapter
import com.example.favmo.view.adapter.ReviewAdapter
import kotlinx.android.synthetic.main.activity_single_movie.*
import kotlinx.android.synthetic.main.fragment_browse_movie.*
import kotlinx.android.synthetic.main.row.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.channels.spi.AbstractSelectionKey

class SingleMovieActivity : AppCompatActivity() {
    private val TAG : String = SingleMovieActivity::class.java.canonicalName
    lateinit var movie:Movie
    private lateinit  var reviews : ArrayList<Review>
    override fun onCreate(savedInstanceState: Bundle?) {
        val apiKey = getString(R.string.api_key)
        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        movie = intent.getParcelableExtra<Movie>("movie") as Movie
//        Toast.makeText(this,"ini diaktivi 2  ${movie.originalTitle}", Toast.LENGTH_LONG).show()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)
        val rvmovies = findViewById(R.id.reviewList) as RecyclerView // Add this
        rvmovies.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true)
        if(movie!=null){

            val imageUrl = StringBuilder()
            imageUrl.append(getString(R.string.base_path_poster)).append(movie.posterPath)
            mvtitle.setText(movie.originalTitle)
            rating_value.setText(movie?.voteaverage.toString())
            popularity_value.setText(movie?.popularity.toString())
            Glide.with(this).load(imageUrl.toString()).into(imageTV)
            overview.setText(movie.overview)
            getReview(apiInterface,apiKey,movie.id.toString())
        }

    }

    fun getReview(apiInterface: ApiInterface,apiKey: String,id:String)
    {
        val call : Call<ReviewResponse> = apiInterface.getReview(id,apiKey)
        call.enqueue(object : Callback<ReviewResponse> {
            override fun onFailure(call: Call<ReviewResponse>?, t: Throwable?) {
                Log.d("$TAG", "Gagal Fetch Review")
            }
            override fun onResponse(call: Call<ReviewResponse>?, response: Response<ReviewResponse>?) {
                Log.d("$TAG",response.toString())
                reviews= response!!.body()!!.results
                Log.d("$TAG", "review size ${reviews.size}")
                reviewList.adapter = ReviewAdapter(reviews )
            }
        })
    }


}
