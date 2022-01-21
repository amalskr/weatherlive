package com.ceylonapz.weatherlive.utilities.workers

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.ceylonapz.weatherlive.utilities.CURRENT_LOCATION
import com.ceylonapz.weatherlive.utilities.getLastKnownLocation

class SplashWorker(context: Context, param: WorkerParameters) : Worker(context, param) {
    override fun doWork(): Result {
        val status = workDataOf(CURRENT_LOCATION to getLastKnownLocation(applicationContext))
        return Result.success(status)
    }
}
