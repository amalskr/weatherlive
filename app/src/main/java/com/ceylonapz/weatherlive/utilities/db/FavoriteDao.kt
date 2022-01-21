package com.ceylonapz.weatherlive.utilities.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * from favorite ORDER BY locationName ASC")
    fun getAll(): Flow<List<Favorite>>

    @Query("SELECT * from favorite ORDER BY locationName ASC")
    fun getFavList(): List<Favorite>

    @Query("SELECT * from favorite WHERE id = :id")
    fun getLocation(id: Int): Flow<Favorite>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(fav: Favorite)

    @Update
    suspend fun update(fav: Favorite)

    @Delete
    suspend fun delete(fav: Favorite)
}