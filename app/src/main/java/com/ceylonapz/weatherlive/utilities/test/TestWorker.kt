package com.ceylonapz.mykots.test

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class TestWorker(context: Context, param: WorkerParameters) : Worker(context, param) {
    override fun doWork(): Result {
        //do some backdound
        println("myWorkoer Success")
        return Result.success()
    }
}
