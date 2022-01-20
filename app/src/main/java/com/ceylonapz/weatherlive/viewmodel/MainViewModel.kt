package com.ceylonapz.weatherlive.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ceylonapz.weatherlive.model.CityWeather
import com.ceylonapz.weatherlive.utilities.network.api.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ForecastRepository) : ViewModel() {

    private val TAG = "appRes"
    val forecastLiveData: MutableLiveData<CityWeather?> = MutableLiveData<CityWeather?>()
    var cityWeatherRes: CityWeather? = null

    val forecastDateTime: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    suspend fun getForecastLocation(searchLocation: String) {
        val results = repository.getForecastResultStream(searchLocation)

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

