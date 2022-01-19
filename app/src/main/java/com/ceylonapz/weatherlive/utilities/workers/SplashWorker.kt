package com.ceylonapz.weatherlive.utilities.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.ceylonapz.weatherlive.utilities.SPLASH_STATUS
import com.ceylonapz.weatherlive.utilities.sleep

class SplashWorker(context: Context, param: WorkerParameters) : Worker(context, param) {
    override fun doWork(): Result {
        sleep()
        val status = workDataOf(SPLASH_STATUS to "splash load completed")
        return Result.success(status)
    }
}
