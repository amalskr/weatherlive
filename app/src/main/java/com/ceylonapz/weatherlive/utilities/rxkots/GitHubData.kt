package com.ceylonapz.weatherlive.utilities.rxkots

import com.google.gson.annotations.SerializedName

//RxDI Task #1
data class GitHubData(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("htmlUrl") val htmlUrl: String,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("language") val language: String,
    @field:SerializedName("stargazersCount") val stargazersCount: Int
) {
}