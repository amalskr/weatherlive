package com.ceylonapz.weatherlive.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ceylonapz.weatherlive.databinding.ActivityDetailsBinding
import com.ceylonapz.weatherlive.model.Days
import com.ceylonapz.weatherlive.utilities.SELECTED_FORECAST_DAY
import com.ceylonapz.weatherlive.utilities.SELECTED_TEMPERATURE
import com.ceylonapz.weatherlive.viewmodel.DayItemViewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        getIntentData()
    }

    private fun getIntentData() {
        val selectedForecastDay = intent.getSerializableExtra(SELECTED_FORECAST_DAY) as Days
        val selectedTemperature = intent.getSerializableExtra(SELECTED_TEMPERATURE) as String
        binding.viewDay = DayItemViewModel(selectedForecastDay, selectedTemperature)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}