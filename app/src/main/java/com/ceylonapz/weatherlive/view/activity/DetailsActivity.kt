package com.ceylonapz.weatherlive.view.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.databinding.ActivityDetailsBinding
import com.ceylonapz.weatherlive.model.Days
import com.ceylonapz.weatherlive.utilities.SELECTED_FORECAST_DAY

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
        binding.viewDay = selectedForecastDay
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}