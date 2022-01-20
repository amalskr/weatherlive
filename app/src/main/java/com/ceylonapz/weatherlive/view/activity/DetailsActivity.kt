package com.ceylonapz.weatherlive.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.utilities.SELECTED_FORECAST_DAY

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        getIntentData()
    }

    private fun getIntentData() {
        val selectedForecastDay = intent.getSerializableExtra(SELECTED_FORECAST_DAY)
    }
}