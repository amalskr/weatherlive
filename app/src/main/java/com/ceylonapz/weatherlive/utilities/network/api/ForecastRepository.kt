package com.ceylonapz.weatherlive.utilities.network.api

import com.ceylonapz.weatherlive.model.ForcastResponse
import com.ceylonapz.weatherlive.utilities.API_KEY
import retrofit2.Response
import javax.inject.Inject

class ForecastRepository @Inject constructor(private val service: ForecastService) {
    suspend fun getForecastResultStream(location: String): Response<ForcastResponse> {
        return service.getForecast(location, API_KEY)
    }
}
