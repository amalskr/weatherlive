package com.ceylonapz.weatherlive.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.databinding.ActivityMainBinding
import com.ceylonapz.weatherlive.model.CityWeather
import com.ceylonapz.weatherlive.model.Days
import com.ceylonapz.weatherlive.model.adapters.ForecastDayAdapter
import com.ceylonapz.weatherlive.view.activity.DetailsActivity
import com.ceylonapz.weatherlive.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "appRes"
    private var searchJob: Job? = null
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fabLocationSearch.setOnClickListener { view ->
            findNewLocation("Colombo")
        }

        mainViewModel.forecastLiveData.observe(this, { cityWeather ->
            Log.d(TAG, "final : $cityWeather")
            updateUI(cityWeather)
        })

        findNewLocation("Matale, Palapathwela")
    }

    private fun findNewLocation(location: String) {
        binding.mainProgressBar.visibility = View.VISIBLE
        binding.recyclerDay.visibility = View.GONE
        binding.txtNoData.visibility = View.GONE
        searchLocation(location)
    }

    private fun updateUI(cityWeather: CityWeather?) {
        if (cityWeather != null) {
            binding.txtNoData.visibility = View.GONE
            binding.recyclerDay.visibility = View.VISIBLE

            binding.recyclerDay.adapter =
                ForecastDayAdapter(cityWeather.days) { selectedDay -> openDetailsView(selectedDay) }
            binding.recyclerDay.setHasFixedSize(true)
        } else {
            binding.txtNoData.visibility = View.VISIBLE
        }
        binding.mainProgressBar.visibility = View.GONE
    }

    private fun openDetailsView(selectedDay: Days) {
        val intent = Intent(applicationContext, DetailsActivity::class.java).apply { }
        startActivity(intent)
    }

    private fun searchLocation(location: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            mainViewModel.getForecastLocation(location)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}