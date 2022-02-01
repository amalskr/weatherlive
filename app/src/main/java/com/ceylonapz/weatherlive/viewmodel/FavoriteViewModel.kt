package com.ceylonapz.weatherlive.viewmodel

import androidx.lifecycle.*
import com.ceylonapz.weatherlive.utilities.db.DatabaseRepository
import com.ceylonapz.weatherlive.utilities.db.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//RoomTask #5
@HiltViewModel
class FavoriteViewModel @Inject constructor(private val dbRepository: DatabaseRepository) :
    ViewModel() {

    val hasSavedLocations: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

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

    //update location
    fun updateLocation(updateLocation: Favorite) {
        viewModelScope.launch {
            dbRepository.editLocation(updateLocation)
            isdbUpdated.postValue("${updateLocation.locationName} Location Updated..!")
        }
    }

    //delete location
    fun deleteLocation(deleteLocation: Favorite) {
        viewModelScope.launch {
            dbRepository.deleteLocation(deleteLocation)
            isdbUpdated.postValue("${deleteLocation.locationName} Location Deleted..!")
        }
    }

    private fun getNewFavoriteEntry(location: String): Favorite {
        return Favorite(
            locationName = location
        )
    }
}