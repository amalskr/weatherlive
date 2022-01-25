package com.ceylonapz.weatherlive.utilities.prefstore

import kotlinx.coroutines.flow.Flow

//datastore task #1
interface DataStoreDao {
    suspend fun setTemperatureType(temperType: String)

    suspend fun getTemperatureType(): Flow<String>
}