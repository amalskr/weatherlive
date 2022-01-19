package com.ceylonapz.weatherlive.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.work.WorkInfo
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.utilities.SPLASH_STATUS
import com.ceylonapz.weatherlive.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    private val TAG = "SplashActivity"
    private val viewModel: SplashViewModel by viewModels {
        SplashViewModel.SplashViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.splashStatusInfo.observe(this, { workInfo ->
            if (workInfo != null) {
                if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                    val status = workInfo.outputData.getString(SPLASH_STATUS)
                    Log.d(TAG, "splashStatusObserver: SUCCESS = $status ")

                    val intent = Intent(this, MainActivity::class.java).apply { }
                    startActivity(intent)
                    finish()
                }

            }
        })
    }
}