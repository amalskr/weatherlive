package com.ceylonapz.weatherlive.model

import com.google.gson.annotations.SerializedName

data class Hours(
    @field:SerializedName("datetime") val datetime: String,
    @field:SerializedName("temp") val temp: Double,
    @field:SerializedName("feelslike") val feelslike: Double,
    @field:SerializedName("humidity") val humidity: Double,
    @field:SerializedName("windspeed") val windspeed: Double,
    @field:SerializedName("conditions") val conditions: String,
    @field:SerializedName("icon") val icon: String
)
