package com.ceylonapz.weatherlive.utilities.network.api

import com.ceylonapz.weatherlive.BuildConfig
import com.ceylonapz.weatherlive.model.CityWeather
import retrofit2.Response
import javax.inject.Inject

class ForecastRepository @Inject constructor(private val service: ForecastService) {
    suspend fun getForecastResultStream(location: String): Response<CityWeather> {
        return service.getForecast(location, BuildConfig.APP_API_KEY)
    }
}
