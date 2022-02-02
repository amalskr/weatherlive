package com.ceylonapz.weatherlive.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ceylonapz.weatherlive.utilities.db.AppDatabase
import com.ceylonapz.weatherlive.utilities.db.DatabaseRepository
import com.ceylonapz.weatherlive.utilities.db.FavoriteDao
import com.google.common.truth.Truth
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteViewModelTest : TestCase() {

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var db: AppDatabase
    private lateinit var dao: FavoriteDao

    @Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
   public override fun setUp() {
        super.setUp()

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries().build()
        dao = db.favoriteDao()
        val dbRepo = DatabaseRepository(dao)
        viewModel = FavoriteViewModel(dbRepo)
    }

    @Test
    fun testFavoriteViewModel() = runBlocking {
    }

    @After
    fun closeDb() {
        db.close()
    }
}