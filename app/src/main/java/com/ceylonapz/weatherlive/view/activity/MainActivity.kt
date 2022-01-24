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
import com.ceylonapz.weatherlive.utilities.network.util.NetworkConnection
import com.ceylonapz.weatherlive.utilities.prefstore.SettingsDataStore
import com.ceylonapz.weatherlive.view.activity.DetailsActivity
import com.ceylonapz.weatherlive.view.activity.FavoriteActivity
import com.ceylonapz.weatherlive.view.activity.SettingsActivity
import com.ceylonapz.weatherlive.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var isNetworkConencted: Boolean = false
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private var myFavoriteList: List<Favorite>? = null
    private var searchJob: Job? = null
    private var gpsLocation: String? = "Califonia"
    private var selectedTempType: String? = "Fahrenheit"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        mainViewModel.setDataStore(SettingsDataStore.getInstance(applicationContext))
        getIntentData()
        startNetworkChecker()
        callLiveDataSets()
        setupClickActions()
    }

    private fun startNetworkChecker() {
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this) { isConnected ->
            isNetworkConencted = isConnected
            findNewLocation()
        }
    }

    private fun setupClickActions() {
        binding.fabLocationSearch.setOnClickListener {

            if (myFavoriteList!!.isNotEmpty()) {
                showFavoriteLocations()
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.pls_add_new_locartion),
                    Toast.LENGTH_LONG
                )
                    .show()
                openFavoriteScreen()
            }
        }
    }

    private fun callLiveDataSets() {
        mainViewModel.allFavoriteLocations.observe(this, { myFavoriteLocations ->
            myFavoriteList = myFavoriteLocations
        })

        mainViewModel.forecastLiveData.observe(this, { cityWeather ->
            updateUI(cityWeather)
        })

        mainViewModel.selectedTempType.observe(this, { type ->
            selectedTempType = type
        })
    }

    private fun getIntentData() {
        if (intent != null && intent.getStringExtra(GPS_LOCATION) != null) {
            gpsLocation = intent.getStringExtra(GPS_LOCATION) as String
            if (gpsLocation == NO_LOCATION) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.cant_get_current_location),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    private fun showFavoriteLocations() {
        val builder = MaterialAlertDialogBuilder(this)
        builder.setTitle(getString(R.string.your_fav_location))

        val locationList = mutableListOf<String>()
        for (fav in myFavoriteList!!) {
            locationList.add(fav.locationName)
        }

        builder.setItems(locationList.toTypedArray()) { dialog, which ->
            gpsLocation = locationList[which]
            findNewLocation()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun findNewLocation() {
        if (isNetworkConencted) {
            binding.mainProgressBar.visibility = View.VISIBLE
            binding.recyclerDay.visibility = View.GONE
            binding.txtNoData.visibility = View.GONE
            searchLocation(gpsLocation!!)
        } else {
            Toast.makeText(applicationContext, getString(R.string.no_network), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun updateUI(cityWeather: CityWeather?) {
        if (cityWeather != null) {
            binding.txtNoData.visibility = View.GONE
            binding.recyclerDay.visibility = View.VISIBLE

            binding.recyclerDay.adapter =
                ForecastDayAdapter(
                    selectedTempType!!,
                    cityWeather.days
                ) { selectedDay -> openDetailsView(selectedDay) }
            binding.recyclerDay.setHasFixedSize(true)
        } else {
            binding.txtNoData.visibility = View.VISIBLE
        }
        binding.mainProgressBar.visibility = View.GONE
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
            R.id.action_settings -> {
                openSettingsScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFavoriteScreen() {
        val intentFavorite =
            Intent(applicationContext, FavoriteActivity::class.java).apply { }
        startActivity(intentFavorite)
    }

    private fun openSettingsScreen() {
        val intentFavorite =
            Intent(applicationContext, SettingsActivity::class.java).apply { }
        startActivity(intentFavorite)
    }

    private fun openDetailsView(selectedDay: Days) {
        val detailsActivity = Intent(applicationContext, DetailsActivity::class.java).apply {
            putExtra(SELECTED_FORECAST_DAY, selectedDay)
        }
        startActivity(detailsActivity)
    }
}
