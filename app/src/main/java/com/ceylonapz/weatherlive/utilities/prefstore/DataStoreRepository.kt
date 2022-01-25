package com.ceylonapz.weatherlive.utilities.prefstore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ceylonapz.weatherlive.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val DATASTORE_NAME = "weather_live_pref"
val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

//datastore task #2
class DataStoreRepository(private val context: Context) : DataStoreDao {

    companion object {
        val TEMPERATURE_TYPE = stringPreferencesKey("temperature_type")
    }

    override suspend fun setTemperatureType(temperType: String) {
        context.datastore.edit { pref ->
            pref[TEMPERATURE_TYPE] = temperType
        }
    }

    override suspend fun getTemperatureType(): Flow<String> {
        return context.datastore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }
            .map { preferences ->
                preferences[TEMPERATURE_TYPE] ?: context.getString(R.string.fahrenheit_name)
            }
    }

}