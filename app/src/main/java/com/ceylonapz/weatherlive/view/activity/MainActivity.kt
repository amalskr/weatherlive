package com.ceylonapz.weatherlive.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.databinding.ActivityMainBinding
import com.ceylonapz.weatherlive.model.CityWeather
import com.ceylonapz.weatherlive.model.Days
import com.ceylonapz.weatherlive.model.adapters.ForecastDayAdapter
import com.ceylonapz.weatherlive.utilities.GPS_LOCATION
import com.ceylonapz.weatherlive.utilities.NO_LOCATION
import com.ceylonapz.weatherlive.utilities.SELECTED_FORECAST_DAY
import com.ceylonapz.weatherlive.utilities.db.Favorite
import com.ceylonapz.weatherlive.view.activity.DetailsActivity
import com.ceylonapz.weatherlive.view.activity.FavoriteActivity
import com.ceylonapz.weatherlive.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var isFocused: Boolean = false;
    private val TAG = "appRes"
    private var searchJob: Job? = null
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        isFocused = hasFocus
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fabLocationSearch.setOnClickListener { view ->
            mainViewModel.allFavoriteLocations.observe(this) { favoriteLocations ->
                if (favoriteLocations.size > 0 && isFocused) {
                    showFavoriteLocations(favoriteLocations)
                } else {
                    Toast.makeText(applicationContext, "Please add new location", Toast.LENGTH_LONG)
                        .show()
                    openFavoriteScreen()
                }
            }
        }

        mainViewModel.forecastLiveData.observe(this, { cityWeather ->
            updateUI(cityWeather)
        })

        getIntentData()
    }

    private fun getIntentData() {
        var gpsLocation = intent.getStringExtra(GPS_LOCATION) as String
        if (gpsLocation.equals(NO_LOCATION)) {
            gpsLocation = "Califonia"
            Toast.makeText(applicationContext, "Can't get your current location", Toast.LENGTH_LONG)
                .show()
        }
        findNewLocation(gpsLocation)
    }

    private fun showFavoriteLocations(favoriteLocations: List<Favorite>?) {
        val builder = MaterialAlertDialogBuilder(this)
        builder.setTitle("Your Favorite Locations")

        val locationList = mutableListOf<String>()
        for (fav in favoriteLocations!!) {
            locationList.add(fav.locationName)
        }

        builder.setItems(locationList.toTypedArray()) { dialog, which ->
            findNewLocation(locationList.get(which))
        }

        val dialog = builder.create()
        dialog.show()
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
        val detailsActivity = Intent(applicationContext, DetailsActivity::class.java).apply {
            putExtra(SELECTED_FORECAST_DAY, selectedDay)
        }
        startActivity(detailsActivity)
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
            R.id.action_favorites -> {
                openFavoriteScreen()
                true
            }
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFavoriteScreen() {
        val intentFavorite =
            Intent(applicationContext, FavoriteActivity::class.java).apply { }
        startActivity(intentFavorite)
    }
}
