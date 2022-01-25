package com.ceylonapz.weatherlive.view.activity

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.databinding.ActivitySettingsBinding
import com.ceylonapz.weatherlive.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint //->//datastore task #5
class SettingsActivity : AppCompatActivity() {

    //private lateinit var settingsDataStore: SettingsDataStore
    private val settViewModel: SettingsViewModel by viewModels()
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = settViewModel

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        observeLiveData()

        binding.radioGrpTempeture.setOnCheckedChangeListener { group, checkedId ->
            val tempRadio: RadioButton = findViewById(checkedId)
            settViewModel.saveTemperType(tempRadio.text.toString())
        }
    }

    private fun observeLiveData() {
        settViewModel.temperType.observe(this, { selecetdTemperType ->
            updateLayout(selecetdTemperType)
        })
    }

    private fun updateLayout(layoutValue: String?) {
        when (layoutValue) {
            getString(R.string.fahrenheit_name) -> binding.radioGrpTempeture.check(
                binding.radioGrpTempeture.getChildAt(
                    0
                ).getId()
            );
            getString(R.string.celsius_name) -> binding.radioGrpTempeture.check(
                binding.radioGrpTempeture.getChildAt(
                    1
                ).getId()
            );
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}