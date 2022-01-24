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

class SettingsDataStore(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: SettingsDataStore? = null
        private const val PREFERENCES_SETTINGS = "settings_preferance"

        fun getInstance(context: Context): SettingsDataStore {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = SettingsDataStore(context)
                INSTANCE = instance
                instance
            }
        }
    }

    private val USER_TEMPERATURE = stringPreferencesKey("temperature_selected")

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = PREFERENCES_SETTINGS
    )

    suspend fun saveTemperatureType(selectedType: String, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[USER_TEMPERATURE] = selectedType
        }
    }

    val getTemperatureType: Flow<String> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[USER_TEMPERATURE] ?: context.getString(R.string.fahrenheit_name)
        }
}