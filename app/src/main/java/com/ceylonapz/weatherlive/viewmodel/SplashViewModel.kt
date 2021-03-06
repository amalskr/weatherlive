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

    var splashStatusInfo: LiveData<WorkInfo>
    val app: Application = application
    val oneTimeRequest = OneTimeWorkRequest.Builder(SplashWorker::class.java).build()
    val workManager = WorkManager.getInstance(app)

    init {
        splashStatusInfo = workManager.getWorkInfoByIdLiveData(oneTimeRequest.id)
    }

    fun callLocationService(isAllowLocation: Boolean) {
        if (isAllowLocation) {
            workManager.enqueue(oneTimeRequest)
        }
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