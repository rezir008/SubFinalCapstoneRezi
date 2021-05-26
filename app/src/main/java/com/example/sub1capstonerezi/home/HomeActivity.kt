package com.example.sub1capstonerezi.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.sub1capstonerezi.R
import com.example.sub1capstonerezi.databinding.ActivityHomeBinding
import com.example.sub1capstonerezi.movie.MovieFragment
import android.provider.Settings
import android.view.MenuItem
import android.widget.Toast
import com.example.sub1capstonerezi.tv.TvFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navChange(MovieFragment())
        binding.tabNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_movie -> navChange(MovieFragment())
            R.id.nav_tv -> navChange(TvFragment())
            R.id.nav_favorite -> moveToFavoriteFragment()
        }
        true
    }

    private val className: String get() = "com.example.favorite.FavFragment"

    private fun moveToFavoriteFragment() {
        val frag = instantiateFragment(className)
        if (frag != null) {
            navChange(frag)
        }
    }

    private fun instantiateFragment(cName: String): Fragment? {
        return try {
            Class.forName(cName).newInstance() as Fragment
        } catch (e: Exception) {
            Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun navChange(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.viewPager, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.setting -> {
                val myIntt = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(myIntt)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}