package com.example.favmo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.favmo.R
import com.example.favmo.data.model.Movie
import kotlinx.android.synthetic.main.fragment_browse_movie.*
import com.example.favmo.view.adapter.MovieAdapter

class BrowseMovie : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    lateinit var movielist: ArrayList<Movie>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var args = Bundle()
         movielist = args.getSerializable("movies") as ArrayList<Movie>
        return inflater.inflate(R.layout.fragment_browse_movie,container,false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView,savedInstanceState)
        rv_movies.apply{
            layoutManager =LinearLayoutManager(activity)

            //set costum adapter
            adapter = MovieAdapter(movielist)
        }
    }
}
