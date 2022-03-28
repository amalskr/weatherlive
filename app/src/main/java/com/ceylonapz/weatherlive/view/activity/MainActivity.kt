package com.ceylonapz.weatherlive.ui

import android.content.Intent
import android.os.Bundle
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
import com.ceylonapz.weatherlive.utilities.GPS_LOCATION
import com.ceylonapz.weatherlive.utilities.NO_LOCATION
import com.ceylonapz.weatherlive.utilities.SELECTED_FORECAST_DAY
import com.ceylonapz.weatherlive.utilities.SELECTED_TEMPERATURE
import com.ceylonapz.weatherlive.utilities.db.Favorite
import com.ceylonapz.weatherlive.utilities.network.util.Status
import com.ceylonapz.weatherlive.view.activity.DetailsActivity
import com.ceylonapz.weatherlive.view.activity.FavoriteActivity
import com.ceylonapz.weatherlive.view.activity.SettingsActivity
import com.ceylonapz.weatherlive.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.infinity.movieapp.extensions.navigateActivity
import com.infinity.movieapp.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@FlowPreview
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var cityWeather: CityWeather? = null
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

        networkStatusTracker()
        getIntentData()
        callLiveDataSets()
        setupClickActions()
    }

    private fun networkStatusTracker() {
        mainViewModel.networkStatus.observe(this) { networkStatus ->
            when (networkStatus) {
                is Status.Fetched -> {
                    isNetworkConencted = true
                    findNewLocation()
                }
                is Status.Error -> {
                    isNetworkConencted = false
                    this.showToast(getString(R.string.no_network))
                }
            }
        }
    }

    private fun setupClickActions() {
        binding.fabLocationSearch.setOnClickListener {

            if (myFavoriteList!!.isNotEmpty()) {
                showFavoriteLocations()
            } else {
                this.showToast(getString(R.string.pls_add_new_locartion))
                openFavoriteScreen()
            }
        }
    }

    private fun callLiveDataSets() {
        mainViewModel.allFavoriteLocations.observe(this) { myFavoriteLocations ->
            myFavoriteList = myFavoriteLocations
        }

        mainViewModel.forecastLiveData.observe(this) { cityWeather ->
            this.cityWeather = cityWeather
            updateUI()
        }

        mainViewModel.selectedTempType.observe(this) { type ->
            selectedTempType = type
            if (gpsLocation == null) {
                updateUI()
            }
        }
    }

    private fun getIntentData() {
        if (intent != null && intent.getStringExtra(GPS_LOCATION) != null) {
            gpsLocation = intent.getStringExtra(GPS_LOCATION) as String
            if (gpsLocation == NO_LOCATION) {
                this.showToast(getString(R.string.cant_get_current_location))
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
            if (gpsLocation != null) {
                binding.mainProgressBar.visibility = View.VISIBLE
                binding.recyclerDay.visibility = View.GONE
                binding.txtNoData.visibility = View.GONE
                searchLocation()
            }
        } else {
            this.showToast(getString(R.string.no_network))
        }
    }

    private fun updateUI() {
        if (cityWeather != null) {
            binding.txtNoData.visibility = View.GONE
            binding.recyclerDay.visibility = View.VISIBLE

            mainViewModel.forecastTemperture.postValue(cityWeather!!.currentConditions.temp)

            binding.recyclerDay.adapter =
                ForecastDayAdapter(
                    selectedTempType!!,
                    cityWeather!!.days
                ) { selectedDay -> openDetailsView(selectedDay) }
            binding.recyclerDay.setHasFixedSize(true)
        } else {
            binding.txtNoData.visibility = View.VISIBLE
        }
        binding.mainProgressBar.visibility = View.GONE
    }

    private fun searchLocation() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            mainViewModel.getForecastLocation(gpsLocation!!)
            gpsLocation = null
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
        val detailsBundle = Bundle()
        detailsBundle.putSerializable(SELECTED_FORECAST_DAY, selectedDay)
        detailsBundle.putString(SELECTED_TEMPERATURE, selectedTempType)
        navigateActivity(DetailsActivity::class.java,detailsBundle,null)
    }
}
