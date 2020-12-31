package com.example.favmo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.favmo.data.db.FavoriteHelper
import com.example.favmo.data.model.Movie
import com.example.favmo.view.BrowseMovie
import com.example.favmo.view.FavoriteMovie
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    private val TAG : String = MainActivity::class.java.canonicalName
    private lateinit var movies : ArrayList<Movie>
    private lateinit var favoriteHelper: FavoriteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteHelper = FavoriteHelper.getInstance(this)
        favoriteHelper.open()
        setContentView(R.layout.activity_main)
        val firstFragment=FavoriteMovie()
        val secondFragment=BrowseMovie()
        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.fav->setCurrentFragment(firstFragment)
                R.id.browse->setCurrentFragment(secondFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

    override fun onDestroy() {
        super.onDestroy()
        favoriteHelper.close()
    }

}


