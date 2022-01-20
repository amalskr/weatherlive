package com.ceylonapz.weatherlive.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ceylonapz.weatherlive.model.CityWeather
import com.ceylonapz.weatherlive.utilities.db.DatabaseRepository
import com.ceylonapz.weatherlive.utilities.db.Favorite
import com.ceylonapz.weatherlive.utilities.network.api.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepo: ForecastRepository,
    private val dbRepo: DatabaseRepository
) : ViewModel() {

    private val TAG = "appRes"
    val forecastLiveData: MutableLiveData<CityWeather?> = MutableLiveData<CityWeather?>()
    var cityWeatherRes: CityWeather? = null

    val forecastDateTime: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    //get all saved locations
    val allFavoriteLocations: LiveData<List<Favorite>> =
        dbRepo.getAllLocations().asLiveData()

    suspend fun getForecastLocation(searchLocation: String) {
        val results = apiRepo.getForecastResultStream(searchLocation)

        if (results.isSuccessful) {
            cityWeatherRes = results.body();
            forecastLiveData.postValue(cityWeatherRes)
            setForecastTime()
        } else {
            forecastLiveData.postValue(null)
            Log.d(TAG, "getForecastLocation : Error")
        }
    }

    private fun setForecastTime() {
        val condition = cityWeatherRes!!.currentConditions.conditions
        val date = cityWeatherRes!!.days[0].datetime
        val time = cityWeatherRes!!.currentConditions.datetime

        forecastDateTime.postValue("$condition | $date $time")
    }


}

