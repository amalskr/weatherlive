package com.ceylonapz.weatherlive.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.model.CityWeather
import com.ceylonapz.weatherlive.utilities.convertTemperature
import com.ceylonapz.weatherlive.utilities.db.DatabaseRepository
import com.ceylonapz.weatherlive.utilities.db.Favorite
import com.ceylonapz.weatherlive.utilities.di.ResourcesProvider
import com.ceylonapz.weatherlive.utilities.network.api.ForecastRepository
import com.ceylonapz.weatherlive.utilities.prefstore.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepo: ForecastRepository,
    private val dbRepo: DatabaseRepository,
    private val res: ResourcesProvider
) : ViewModel() {

    private val TAG = "appRes"
    val forecastLiveData: MutableLiveData<CityWeather?> = MutableLiveData<CityWeather?>()
    var cityWeatherRes: CityWeather? = null
    lateinit var pref: SettingsDataStore
    var selecetdTempType: MutableLiveData<String> =
        MutableLiveData(res.getString(R.string.fahrenheit_format))

    val forecastDateTime: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val allFavoriteLocations: LiveData<List<Favorite>> =
        dbRepo.getAllLocations().asLiveData()

    val myFavList: MutableLiveData<List<Favorite>> by lazy {
        MutableLiveData<List<Favorite>>()
    }

    fun getFevList() {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                myFavList.postValue(dbRepo.getFevLocations())
            }
        }
    }

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

    fun getTemperature(temperature: Double): String {
        return convertTemperature(temperature, res.getString(R.string.celsius_name))
    }

    fun getSelectedTempetureName() {
        viewModelScope.launch {
            pref.getTemperatureType.flowOn(Dispatchers.IO).collect { selectedType ->
                selecetdTempType.postValue(selectedType)
            }
        }
    }

    fun getTemperatureFormat(type: String): String {
        return when (type) {
            res.getString(R.string.celsius_name) -> res.getString(R.string.celsius_format)
            res.getString(R.string.fahrenheit_name) -> res.getString(R.string.fahrenheit_format)
            else -> {
                ""
            }
        }
    }

    fun setDataStore(pref: SettingsDataStore) {
        this.pref = pref
        getSelectedTempetureName()
    }

    fun getSelectedType(): LiveData<String> {
        return pref.getTemperatureType.asLiveData()
    }

}

