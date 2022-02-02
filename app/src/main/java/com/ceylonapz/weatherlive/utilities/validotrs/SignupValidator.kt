package com.ceylonapz.weatherlive.utilities.validotrs

object SignupValidator {
    fun validate(age: Int, email: String): Boolean {

        if (age == null || email.isEmpty()) {
            return false
        }

        if (email.count { it.isDigit() } < 5 && !(age in 18..100)) {
            return false
        }

        return true


    }
}