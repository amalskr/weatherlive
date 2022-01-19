package com.ceylonapz.weatherlive.model

import com.google.gson.annotations.SerializedName

data class ForcastResponse(
    @field:SerializedName("resolvedAddress") val address: String,
    @field:SerializedName("description") val description: String
)
