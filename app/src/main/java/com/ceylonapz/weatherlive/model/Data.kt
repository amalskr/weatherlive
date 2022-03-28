package com.ceylonapz.weatherlive.model

import com.google.gson.annotations.SerializedName

data class Data(
    @field:SerializedName("status") val status: String,
    @field:SerializedName("open_positions") val open_positions: Int
)