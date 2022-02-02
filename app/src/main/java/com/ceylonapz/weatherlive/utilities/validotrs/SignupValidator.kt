package com.ceylonapz.weatherlive.utilities.validotrs

object SignupValidator {
    fun validate(age: Int, email: String): Boolean {
        return (age in 18..100) && email.isNotEmpty()
    }
}