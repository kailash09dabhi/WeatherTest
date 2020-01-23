package com.kailashdabhi.weather

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

  @Rule
  @JvmField
  var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

  @Rule
  @JvmField
  var mGrantPermissionRule =
    GrantPermissionRule.grant(
      "android.permission.ACCESS_COARSE_LOCATION"
    )

  @Test
  fun mainActivityTest2() {
    val appCompatEditText = onView(
      allOf(
        withId(R.id.input),
        childAtPosition(
          childAtPosition(
            withId(R.id.fragmentContainer),
            0
          ),
          0
        ),
        isDisplayed()
      )
    )
    appCompatEditText.perform(replaceText("mumbai"), closeSoftKeyboard())

    val appCompatEditText2 = onView(
      allOf(
        withId(R.id.input), withText("mumbai"),
        childAtPosition(
          childAtPosition(
            withId(R.id.fragmentContainer),
            0
          ),
          0
        ),
        isDisplayed()
      )
    )
    appCompatEditText2.perform(click())

    val appCompatEditText3 = onView(
      allOf(
        withId(R.id.input), withText("mumbai"),
        childAtPosition(
          childAtPosition(
            withId(R.id.fragmentContainer),
            0
          ),
          0
        ),
        isDisplayed()
      )
    )
    appCompatEditText3.perform(replaceText("mumbai,Pune,surat"))

    val appCompatEditText4 = onView(
      allOf(
        withId(R.id.input), withText("mumbai,Pune,surat"),
        childAtPosition(
          childAtPosition(
            withId(R.id.fragmentContainer),
            0
          ),
          0
        ),
        isDisplayed()
      )
    )
    appCompatEditText4.perform(closeSoftKeyboard())

    val appCompatImageView = onView(
      allOf(
        withId(R.id.searchIcon),
        childAtPosition(
          childAtPosition(
            withId(R.id.fragmentContainer),
            0
          ),
          1
        ),
        isDisplayed()
      )
    )
    appCompatImageView.perform(click())
    Thread.sleep(5000)

    val actionMenuItemView = onView(
      allOf(
        withId(R.id.action_forecast), withText("Forecast"),
        childAtPosition(
          childAtPosition(
            withId(R.id.action_bar),
            1
          ),
          0
        ),
        isDisplayed()
      )
    )
    actionMenuItemView.perform(click())

    Thread.sleep(5000)

    val appCompatSpinner = onView(
      allOf(
        withId(R.id.spinner),
        childAtPosition(
          childAtPosition(
            withId(R.id.fragmentContainer),
            0
          ),
          0
        ),
        isDisplayed()
      )
    )
    appCompatSpinner.perform(click())

    Thread.sleep(250)

  }

  private fun childAtPosition(
    parentMatcher: Matcher<View>, position: Int
  ): Matcher<View> {

    return object : TypeSafeMatcher<View>() {
      override fun describeTo(description: Description) {
        description.appendText("Child at position $position in parent ")
        parentMatcher.describeTo(description)
      }

      public override fun matchesSafely(view: View): Boolean {
        val parent = view.parent
        return parent is ViewGroup && parentMatcher.matches(parent)
            && view == parent.getChildAt(position)
      }
    }
  }
}
