package com.ceylonapz.weatherlive.utilities.rxkots

import com.ceylonapz.weatherlive.utilities.network.api.ForecastService
import com.ceylonapz.weatherlive.utilities.rxkots.GitHubClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//RxDI Task #4
@InstallIn(SingletonComponent::class)
@Module
object RxKotsModule {
    @Singleton
    @Provides
    fun provideForecastService(): GitHubClient {
        return GitHubClient.create()
    }
}