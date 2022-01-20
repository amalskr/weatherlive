package com.ceylonapz.weatherlive.model

import com.google.gson.annotations.SerializedName

data class CityWeather(
    @field:SerializedName("resolvedAddress") val address: String,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("days") val days: List<Days>,
    @field:SerializedName("currentConditions") val currentConditions: CurrentConditions
)
