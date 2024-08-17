package com.example.movieapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.view.fragment.CartoonFragment
import com.example.movieapp.view.fragment.MoviesFragment
import com.example.movieapp.view.fragment.MoviesSeriesFragment
import com.example.movieapp.view.fragment.TvShowsFragment
import com.example.movieapp.view.searchmovie.SearchMovieActivity
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchMovieActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragment = when (tab?.position) {
                    0 -> MoviesFragment()
                    1 -> MoviesSeriesFragment()
                    2 -> CartoonFragment()
                    3 -> TvShowsFragment()
                    else -> throw IllegalArgumentException("Unexpected position")
                }

                supportFragmentManager.beginTransaction().replace(R.id.fragment_con_view, fragment)
                    .commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.tabLayout.getTabAt(0)?.select()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_con_view, MoviesFragment())
            .commit()
    }

}
