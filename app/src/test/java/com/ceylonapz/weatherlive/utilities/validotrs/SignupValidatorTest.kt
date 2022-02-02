package com.ceylonapz.weatherlive.utilities.validotrs

import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SignupValidatorTest {

    @Test
    fun whenInputIsValid() {
        val age = 18
        val email = "randam@gmail.com"
        val testResults = SignupValidator.validate(age, email)

        Truth.assertThat(testResults).isEqualTo(true)
    }

    @Test
    fun whenInputsIsInvalid() {
        val age = 101
        val email = ""
        val testResults = SignupValidator.validate(age, email)

        Truth.assertThat(testResults).isEqualTo(false)
    }
}