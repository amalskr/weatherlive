package com.ceylonapz.weatherlive.viewmodel

import androidx.lifecycle.ViewModel
import com.ceylonapz.weatherlive.model.Days
import com.ceylonapz.weatherlive.utilities.convertTemperature

class DayItemViewModel(viewDay: Days, val type: String) : ViewModel() {
    val day = viewDay

    fun getFormattedTemperture(tempeture: Double): String {
        return convertTemperature(tempeture, type, true)
    }
}
