package com.ceylonapz.weatherlive.viewmodel

import androidx.lifecycle.*
import com.ceylonapz.weatherlive.utilities.db.DatabaseRepository
import com.ceylonapz.weatherlive.utilities.db.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val dbRepository: DatabaseRepository) :
    ViewModel() {

    //get all saved locations
    val allFavoriteLocations: LiveData<List<Favorite>> =
        dbRepository.getAllLocations().asLiveData()

    val isdbUpdated = MutableLiveData<String>()

    //add new location
    fun addNewLocation(location: String) {
        val newLocation = getNewFavoriteEntry(location)
        viewModelScope.launch {
            dbRepository.addNewLocation(newLocation)
            isdbUpdated.postValue("$location Location Added..!")
        }
    }

    private fun getNewFavoriteEntry(location: String): Favorite {
        return Favorite(
            locationName = location
        )
    }
}