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

    suspend fun getForecastLocation(searchLocation: String) {
        val results = repository.getForecastResultStream(searchLocation)

        if (results.isSuccessful) {
            val responseResults = results.body();
            forecastLiveData.postValue(responseResults)
        } else {
            Log.d(TAG, "getForecastLocation : Error")
        }

    }

}

