package com.ceylonapz.weatherlive.utilities.di

import android.content.Context
import com.ceylonapz.weatherlive.utilities.db.AppDatabase
import com.ceylonapz.weatherlive.utilities.db.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//RoomTask #3
@InstallIn(SingletonComponent::class) //-> (Hilt generates)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun providePlantDao(appDatabase: AppDatabase): FavoriteDao {
        return appDatabase.favoriteDao()
    }
}