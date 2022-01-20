package com.ceylonapz.weatherlive.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ceylonapz.weatherlive.databinding.ActivityDetailsBinding
import com.ceylonapz.weatherlive.model.Days
import com.ceylonapz.weatherlive.utilities.SELECTED_FORECAST_DAY

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentData()
    }

    private fun getIntentData() {
        val selectedForecastDay = intent.getSerializableExtra(SELECTED_FORECAST_DAY) as Days
        binding.viewDay = selectedForecastDay
    }
}