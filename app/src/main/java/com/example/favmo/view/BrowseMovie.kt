package com.example.favmo.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.favmo.R
import com.example.favmo.data.model.Movie
import com.example.favmo.data.model.MovieResponse
import com.example.favmo.data.service.ApiClient
import com.example.favmo.data.service.ApiInterface
import kotlinx.android.synthetic.main.fragment_browse_movie.*
import com.example.favmo.view.adapter.MovieAdapter
import com.example.favmo.view.detail.SingleMovieActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BrowseMovie : Fragment(),CellClickListener {
    private val TAG : String = BrowseMovie::class.java.canonicalName
    private lateinit var movies : ArrayList<Movie>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_browse_movie, container, false)
        val rvmovies = rootView.findViewById(R.id.rv_movies) as RecyclerView // Add this
        rvmovies.layoutManager = LinearLayoutManager(activity)
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = getString(R.string.api_key)
        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        getToRateMovie(apiInterface, apiKey)
        getPopularMovies(apiInterface, apiKey)

    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {

        }
    fun getPopularMovies(apiInterface: ApiInterface, apiKey : String) {
        val call : Call<MovieResponse> = apiInterface.getPopularMovie(apiKey)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                Log.d("$TAG", "Gagal Fetch Popular Movie")
            }

            override fun onResponse(call: Call<MovieResponse>?, response: Response<MovieResponse>?) {
                movies = response!!.body()!!.results
                Log.d("$TAG", "Movie size ${movies.size}")
                rv_movies.adapter = MovieAdapter(movies,this@BrowseMovie)
            }

        })
    }

    fun getToRateMovie(apiInterface: ApiInterface, apiKey : String) {
        val call : Call<MovieResponse> = apiInterface.getMovieTopRated(apiKey)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                Log.d("$TAG", "Gagal Fetch Popular Movie")
            }

            override fun onResponse(call: Call<MovieResponse>?, response: Response<MovieResponse>?) {
                movies = response!!.body()!!.results
                Log.d("$TAG", "Movie size ${movies.size}")
                rv_movies.adapter = MovieAdapter(movies,this@BrowseMovie)
            }

        })
    }

    fun getLatestMovie(apiInterface: ApiInterface, apiKey : String) : Movie? {
        var movie : Movie? = null
        val call : Call<Movie> = apiInterface.getMovieLatest(apiKey)
        call.enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>?, t: Throwable?) {
                Log.d("$TAG", "Gagal Fetch Popular Movie")
            }

            override fun onResponse(call: Call<Movie>?, response: Response<Movie>?) {
                if (response != null) {
                    var originalTitle : String? = response.body()?.originalTitle
                    var posterPath : String? = response.body()?.posterPath

                    if (posterPath == null) {
                    } else {
                        val imageUrl = StringBuilder()
                        imageUrl.append(getString(R.string.base_path_poster)).append(posterPath)
                    }
                }
            }

        })

        return movie
    }

    override fun onCellClickListener(data : Movie) {
        //Toast.makeText(requireContext(),"Cell clicked ${data}", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), SingleMovieActivity::class.java)
        intent.putExtra("movie",data)
        startActivity(intent)
    }


}
