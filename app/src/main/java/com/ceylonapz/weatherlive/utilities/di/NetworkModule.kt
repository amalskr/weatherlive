package com.ceylonapz.weatherlive.utilities.di

import com.ceylonapz.weatherlive.utilities.network.api.ForecastService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideForecastService(): ForecastService {
        return ForecastService.create()
    }
}