package com.ceylonapz.weatherlive.utilities.rxkots

import com.ceylonapz.weatherlive.model.Data
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//RxDI Task #2
interface GitHubClient {
    companion object {
        private val GITHUB_BASE_URL = "https://api.github.com/"
        private val TEMPER_BASE_URL = "https://temper.works/"

        fun create(): GitHubClient {

            val gson =
                GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()

            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(GITHUB_BASE_URL)
                //.baseUrl(TEMPER_BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubClient::class.java)

        }
    }

    @GET("users/{user}/starred")
    fun getStarredRepositories(@Path("user") userName: String?): Single<List<GitHubData>>

    //api/v3/shifts?Date=2021-07-19
    @GET("api/v3/shifts")
    fun getAllJobs(@Query("date") selected_date: String): Single<List<Data>>
}