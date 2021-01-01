package com.example.favmo.view

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.favmo.R
import com.example.favmo.data.db.DatabaseContract.FavoriteColumns
import com.example.favmo.data.db.DatabaseHelper
import com.example.favmo.data.db.FavoriteHelper
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

class BrowseMovie(private var listType : Int,private var favoriteHelper: FavoriteHelper) : Fragment(),CellClickListener,FavoriteClickListner,
    LoadMoreListener {
    private val TAG : String = BrowseMovie::class.java.canonicalName
    private var movies : MutableList<Movie> = ArrayList()
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
        selectGetMovie(listType,apiKey,apiInterface,1)
    }

   private fun selectGetMovie(listType: Int , apiKey: String, apiInterface: ApiInterface, page: Int)
    {
        if (listType == 1)
        {
            getToRateMovie(apiInterface,apiKey,page)
        }
        else if(listType==2)
        {
            getPopularMovies(apiInterface,apiKey,page)
        }
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        }
    fun getPopularMovies(apiInterface: ApiInterface, apiKey : String,page : Int) {
        val call : Call<MovieResponse> = apiInterface.getPopularMovie(apiKey,page)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                Log.d("$TAG", "Gagal Fetch Popular Movie")
            }

            override fun onResponse(call: Call<MovieResponse>?, response: Response<MovieResponse>?) {
                movies.addAll(response!!.body()!!.results)
                Log.d("$TAG", "Movie size ${movies.size}")
                rv_movies.adapter = MovieAdapter(movies as ArrayList<Movie>,this@BrowseMovie,this@BrowseMovie,this@BrowseMovie)
            }

        })
    }

    fun getToRateMovie(apiInterface: ApiInterface, apiKey : String,page: Int) {
        val call : Call<MovieResponse> = apiInterface.getMovieTopRated(apiKey,page)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                Log.d("$TAG", "Gagal Fetch Top rate Movie")
            }
            override fun onResponse(call: Call<MovieResponse>?, response: Response<MovieResponse>?) {
                movies.addAll(response!!.body()!!.results)
                Log.d("$TAG", "Movie size ${movies.size}")
                rv_movies.adapter = MovieAdapter(movies as ArrayList<Movie>,this@BrowseMovie,this@BrowseMovie,this@BrowseMovie)
            }
        })
    }



    override fun onCellClickListener(data : Movie) {
        //Toast.makeText(requireContext(),"Cell clicked ${data}", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), SingleMovieActivity::class.java)
        intent.putExtra("movie",data)
        startActivity(intent)
    }

    override fun onFavoriteClickListener(data: Movie) {
        val dbHelper = DatabaseHelper(requireContext())
        Toast.makeText(requireContext(),"button clicked ${data}", Toast.LENGTH_SHORT).show()
        val values = ContentValues().apply {
            put(FavoriteColumns._ID, data.id)
            put(FavoriteColumns.TITLE, data.originalTitle)
            put(FavoriteColumns.OVERVIEW, data.overview)
            put(FavoriteColumns.POSTERPATH, data.posterPath)
            put(FavoriteColumns.POPULARITY, data.popularity)
            put(FavoriteColumns.VOTEAVERAGE, data.voteaverage)
        }
        favoriteHelper.insert(values)
    }

    override fun onLoadMoreListener(page : Int) {
        getPopularMovies(ApiClient.getClient().create(ApiInterface::class.java),getString(R.string.api_key),page)
    }

    override fun onDetach() {
        movies =  ArrayList()
        super.onDetach()
    }


}
