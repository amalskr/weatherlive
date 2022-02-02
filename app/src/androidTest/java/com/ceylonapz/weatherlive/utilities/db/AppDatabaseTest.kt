package com.ceylonapz.weatherlive.utilities.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: FavoriteDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.favoriteDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun addLocation() = runBlocking {
        val favLocation = Favorite(10, "Naula")
        dao.insert(favLocation)

        val allLocations = dao.getFavList()
        Truth.assertThat(allLocations.contains(favLocation)).isTrue()
    }

    @Test
    fun updateLocation() = runBlocking {
        val favLocationAdd = Favorite(10, "Naula")
        dao.insert(favLocationAdd)

        val favLocation = Favorite(10, "Naula Matale")
        dao.update(favLocation)

        val allLocations = dao.getFavList()
        Truth.assertThat(allLocations.contains(favLocation)).isTrue()
    }

    @Test
    fun searchLocation() = runBlocking {
        val favLocationAdd = Favorite(1, "Naula")
        dao.insert(favLocationAdd)

        //test selected object
        val selectedLocation = dao.getLocation(1).first()
        Truth.assertThat(selectedLocation.locationName == favLocationAdd.locationName).isTrue()
    }

    @Test
    fun searchLocationInList() = runBlocking {
        val favLocationAdd = Favorite(1, "Naula")
        dao.insert(favLocationAdd)

        val locationList = dao.getFavList().toList()
        Truth.assertThat(locationList.contains(favLocationAdd)).isTrue()
    }

    @Test
    fun deleteLocation() = runBlocking {
        val favLocation = Favorite(10, "Naula Matale")
        dao.delete(favLocation)

        val allLocations = dao.getFavList()
        Truth.assertThat(allLocations.contains(favLocation)).isFalse()
    }
}