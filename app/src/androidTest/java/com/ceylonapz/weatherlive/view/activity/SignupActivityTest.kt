package com.ceylonapz.weatherlive.view.activity

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.ceylonapz.weatherlive.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SignupActivityTest {


    @get:Rule
    var activityScenarioRule = activityScenarioRule<SignupActivity>()

    @Test
    fun checkActivityVisibility() {
        Espresso.onView(ViewMatchers.withId(R.id.layoutViewSignup)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun checkSignupButtonVisibility() {
        Espresso.onView(ViewMatchers.withId(R.id.btnAdd)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun checkSignupButtonTextVisibility() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        val signupText = context.resources.getString(R.string.signup)

        Espresso.onView(ViewMatchers.withId(R.id.btnAdd)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(R.string.app_name)
            )
        )
    }

    @Test
    fun checkSignupButtonClickble() {
        Espresso.onView(ViewMatchers.withId(R.id.btnAdd)).perform(ViewActions.click())
    }

    @Test
    fun checkSignupButtonClickbleAndNavigate() {
        //chk clickable
        Espresso.onView(ViewMatchers.withId(R.id.btnAdd)).perform(ViewActions.click())

        //chk navigatable
        Espresso.onView(ViewMatchers.withId(R.id.layoutViewMain)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun checkNavigateBack() {
        //chk clickable
        Espresso.onView(ViewMatchers.withId(R.id.btnAdd)).perform(ViewActions.click())

        //chk navigatable
        Espresso.onView(ViewMatchers.withId(R.id.layoutViewMain)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )

        //chck backpress
        Espresso.pressBack()


        //after backpress should display signup screen
        Espresso.onView(ViewMatchers.withId(R.id.layoutViewSignup)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }
}