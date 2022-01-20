package com.ceylonapz.weatherlive.utilities

import android.util.Log

private const val TAG = "Util"

/**
 * Method for sleeping for a fixed amount of time to emulate slower work
 */
fun sleep() {
    try {
        Thread.sleep(1000, 0)
    } catch (e: InterruptedException) {
        Log.e(TAG, e.message.toString())
    }

}
