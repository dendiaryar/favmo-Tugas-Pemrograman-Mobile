package com.example.favmo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.favmo.data.model.Movie
import com.example.favmo.data.model.MovieResponse
import com.example.favmo.data.service.ApiClient
import com.example.favmo.data.service.ApiInterface
import com.example.favmo.view.BrowseMovie
import com.example.favmo.view.FavoriteMovie
import com.example.favmo.view.WatchList
import com.example.favmo.view.adapter.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private val TAG : String = MainActivity::class.java.canonicalName
    private lateinit var movies : ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val firstFragment=FavoriteMovie()
        val secondFragment=BrowseMovie()
        val thirdFragment=WatchList()

        setCurrentFragment(secondFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.fav->setCurrentFragment(firstFragment)
                R.id.browse->setCurrentFragment(secondFragment)
                R.id.watchlist->setCurrentFragment(thirdFragment)

            }
            true
        }
    }
    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }


}


