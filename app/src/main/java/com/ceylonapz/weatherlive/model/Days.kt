package com.ceylonapz.weatherlive.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Days(
    @field:SerializedName("datetime") val datetime: String,
    @field:SerializedName("tempmax") val tempmax: Double,
    @field:SerializedName("tempmin") val tempmin: Double,
    @field:SerializedName("temp") val temp: Double,
    @field:SerializedName("snow") val snow: Double,
    @field:SerializedName("windspeed") val windspeed: Double,
    @field:SerializedName("cloudcover") val cloudcover: Double,
    @field:SerializedName("visibility") val visibility: Double,
    @field:SerializedName("uvindex") val uvindex: Double,
    @field:SerializedName("sunrise") val sunrise: String,
    @field:SerializedName("sunset") val sunset: String,
    @field:SerializedName("conditions") val conditions: String,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("icon") val icon: String,
    @field:SerializedName("humidity") val humidity: Double,
    @field:SerializedName("hours") val hours: List<Hours>
) : Serializable
