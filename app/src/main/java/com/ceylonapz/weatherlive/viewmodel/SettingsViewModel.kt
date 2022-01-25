package com.ceylonapz.weatherlive.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ceylonapz.weatherlive.utilities.prefstore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

//datastore task #4
@HiltViewModel
class SettingsViewModel @Inject constructor(private val prefRepo: DataStoreRepository) :
    ViewModel() {

    var temperType: MutableLiveData<String> = MutableLiveData("")

    init {
        getPrefSelections()
    }

    fun saveTemperType(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            prefRepo.setTemperatureType(type)
        }
    }

    private fun getPrefSelections() {
        viewModelScope.launch(Dispatchers.IO) {
            prefRepo.getTemperatureType().collect { savedTemperType ->
                temperType.postValue(savedTemperType)
            }
        }
    }
}