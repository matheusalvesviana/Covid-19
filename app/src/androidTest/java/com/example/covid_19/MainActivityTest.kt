package com.example.covid_19

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun run_cycle_button_disabled() {
        onView(withId(R.id.button_run_cycle))
            .check(matches(not(isEnabled())))
    }

    @Test
    fun run_cycle_button_enabled() {
        onView(withId(R.id.et_cycle)).perform(replaceText("2"))
        onView(withId(R.id.button_run_cycle))
            .check(matches(isEnabled()))
    }

    @Test
    fun run_cycle_button_click() {
        onView(withId(R.id.et_cycle)).perform(replaceText("2"))
        onView(withId(R.id.button_run_cycle))
            .perform(click())
        onView(withId(R.id.button_run_cycle))
            .check(matches(isEnabled()))
    }

    @Test
    fun status_button_click() {
        onView(withId(R.id.et_cycle)).perform(replaceText("2"))
        onView(withId(R.id.button_run_cycle))
            .perform(click())
        onView(withId(R.id.button_status))
            .perform(click())
        onView(withText("Informação"))
            .check(matches(isDisplayed()))
    }
}
