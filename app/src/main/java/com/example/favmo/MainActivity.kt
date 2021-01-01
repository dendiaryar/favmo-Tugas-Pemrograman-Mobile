package com.example.favmo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    private var movieType : Int = 1
    private lateinit var firstFragment:Fragment
    private lateinit  var secondFragment:Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firstFragment=FavoriteMovie()
        secondFragment=BrowseMovie(movieType)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId)
        {
            R.id.getTopRatedMovies ->{
                supportFragmentManager.beginTransaction().remove(secondFragment).commitAllowingStateLoss()
                movieType = 1
                secondFragment = BrowseMovie(movieType)
                supportFragmentManager.beginTransaction().add(R.id.flFragment, secondFragment).commit()
                true
            }
            R.id.getLatestMovies ->{
                supportFragmentManager.beginTransaction().remove(secondFragment).commitAllowingStateLoss()
                movieType = 3
                secondFragment = BrowseMovie(movieType)
                supportFragmentManager.beginTransaction().add(R.id.flFragment, secondFragment).commit()
                true
            }
            R.id.getPopularMovies ->
            {
                supportFragmentManager.beginTransaction().remove(secondFragment).commitAllowingStateLoss()
                movieType = 2
                secondFragment = BrowseMovie(movieType)
                supportFragmentManager.beginTransaction().add(R.id.flFragment, secondFragment).commit()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }



}


