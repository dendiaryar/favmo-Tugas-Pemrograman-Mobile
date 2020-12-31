package com.example.favmo.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.favmo.R
import com.example.favmo.data.db.FavoriteHelper
import com.example.favmo.data.helper.MappingHelper
import com.example.favmo.data.model.Movie

import com.example.favmo.view.adapter.FavoriteAdapter
import com.example.favmo.view.detail.SingleMovieActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteMovie : Fragment(),CellClickListener {
    private val TAG : String = FavoriteMovie::class.java.canonicalName
    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteHelper: FavoriteHelper

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    private lateinit var movies : ArrayList<Movie>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite_movie, container, false)
        val rvmovies = rootView.findViewById(R.id.movies_rv) as RecyclerView // Add this
        rvmovies.layoutManager = LinearLayoutManager(activity)
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteHelper = FavoriteHelper.getInstance(requireContext())
        favoriteHelper.open()
        adapter = FavoriteAdapter(this)

        loadNotesAsync()

        if (savedInstanceState == null) {
            // proses ambil data
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Movie>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavorites = list
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(movies_rv, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = favoriteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                adapter.listFavorites = notes
                movies_rv.adapter = adapter
            } else {
                adapter.listFavorites = ArrayList()
                movies_rv.adapter = adapter
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavorites)
    }

    override fun onCellClickListener(data : Movie)
    {
        val intent = Intent(requireContext(), SingleMovieActivity::class.java)
        intent.putExtra("movie",data)
        startActivity(intent)
    }


}
