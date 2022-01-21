package com.ceylonapz.weatherlive.utilities.db

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(private val favoriteDao: FavoriteDao) {

    fun getAllLocations() = favoriteDao.getAll()

    fun getFevLocations() = favoriteDao.getFavList()

    fun getLocation(id: Int) = favoriteDao.getLocation(id)

    suspend fun addNewLocation(fav: Favorite) = favoriteDao.insert(fav)

    suspend fun editLocation(fav: Favorite) = favoriteDao.update(fav)

    suspend fun deleteLocation(fav: Favorite) = favoriteDao.delete(fav)
}
