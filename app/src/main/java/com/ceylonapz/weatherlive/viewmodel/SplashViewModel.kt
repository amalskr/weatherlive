package com.ceylonapz.weatherlive.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ceylonapz.weatherlive.utilities.workers.SplashWorker


class SplashViewModel(application: Application) : ViewModel() {

    internal val splashStatusInfo: LiveData<WorkInfo>

    init {
        val oneTimeRequest = OneTimeWorkRequest.Builder(SplashWorker::class.java).build()
        val workManager = WorkManager.getInstance(application)

        workManager.enqueue(oneTimeRequest)

        splashStatusInfo = workManager.getWorkInfoByIdLiveData(oneTimeRequest.id)
    }

    @Suppress("UNCHECKED_CAST")
    class SplashViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
                SplashViewModel(application) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

    }
}