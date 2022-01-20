package com.ceylonapz.weatherlive.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.databinding.ActivityMainBinding
import com.ceylonapz.weatherlive.model.CityWeather
import com.ceylonapz.weatherlive.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "appRes"
    private var searchJob: Job? = null
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fabLocationSearch.setOnClickListener { view ->
            searchLocation("Palapathwela") //38.9697,-77.385
        }

        viewModel.forecastLiveData.observe(this, { cityWeather ->
            Log.d(TAG, "final : $cityWeather")
            updateUI(cityWeather)
        })
    }

    private fun updateUI(cityWeather: CityWeather?) {
        if (cityWeather != null) {
            //binding.btnSearch.text = cityWeather.currentConditions.conditions
        }
    }

    private fun searchLocation(location: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getForecastLocation(location)
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