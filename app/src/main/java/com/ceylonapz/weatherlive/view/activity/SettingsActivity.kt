package com.ceylonapz.weatherlive.view.activity

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.databinding.ActivitySettingsBinding
import com.ceylonapz.weatherlive.utilities.prefstore.SettingsDataStore
import com.ceylonapz.weatherlive.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch


class SettingsActivity : AppCompatActivity() {

    private lateinit var settingsDataStore: SettingsDataStore
    private val settViewModel: SettingsViewModel by viewModels()
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = settViewModel
        binding.lifecycleOwner = this

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        initDataStore()

        binding.radioGrpTempeture.setOnCheckedChangeListener { group, checkedId ->
            val tempRadio: RadioButton = findViewById(checkedId)
            updateTempType(tempRadio.text.toString())
        }
    }

    private fun initDataStore() {
        settingsDataStore = SettingsDataStore.getInstance(applicationContext)

        settingsDataStore.getTemperatureType.asLiveData().observe(this, { layoutValue ->
            updateLayout(layoutValue)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun updateLayout(layoutValue: String?) {
        when (layoutValue) {
            getString(R.string.fahrenheit) -> binding.radioGrpTempeture.check(
                binding.radioGrpTempeture.getChildAt(
                    0
                ).getId()
            );
            getString(R.string.celsius) -> binding.radioGrpTempeture.check(
                binding.radioGrpTempeture.getChildAt(
                    1
                ).getId()
            );
        }
    }

    private fun updateTempType(selectedType: String) {
        lifecycleScope.launch {
            settingsDataStore.saveTemperatureType(selectedType, applicationContext)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}