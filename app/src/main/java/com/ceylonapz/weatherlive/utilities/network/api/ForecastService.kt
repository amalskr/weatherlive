package com.ceylonapz.weatherlive.utilities.network.api

import com.ceylonapz.weatherlive.model.CityWeather
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ForecastService {
    companion object {
        /*https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/38.9697,-77.385?key=YOUR_API_KEY*/
        private const val BASE_URL =
            "https://weather.visualcrossing.com/VisualCrossingWebServices/"

        fun create(): ForecastService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ForecastService::class.java)
        }
    }

    @GET("rest/services/timeline/{location}")
    suspend fun getForecast(
        @Path("location") location: String,
        @Query("key") key: String
    ): Response<CityWeather>

}