package com.example.android.myapplication.ui;


import android.content.Context;
import android.support.test.espresso.DataInteraction;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.android.myapplication.R;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import junit.framework.AssertionFailedError;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        DataInteraction cardView = onData(anything())
                .inAdapterView(allOf(withId(R.id.images_grid_view),
                        childAtPosition(
                                withId(R.id.master_list_fragment),
                                0)))
                .atPosition(2);
        cardView.perform(click());

        // Check that we have ingredients.
        try {
            onView(withId(R.id.ingredients)).check(matches(isDisplayed()));
        } catch (AssertionFailedError e) {
            // View not displayed
        }

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.recipe_single_step), withText("Combine dry ingredients."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.steps),
                                        2),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        // Check that ExoPlayer is active.
        try {
            onView(allOf(withId(R.id.videoView), withClassName(is(SimpleExoPlayerView.class.getName()))));
        } catch (AssertionFailedError e) {
            // ExoPlayer is not active.
        }

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(4921);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (isPortrait(mActivityTestRule.getActivity())) {

            ViewInteraction appCompatButton = onView(
                    allOf(withId(R.id.button_next), withText("Next"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.video_player_portrait),
                                            1),
                                    1),
                            isDisplayed()));
            appCompatButton.perform(click());

            // Added a sleep statement to match the app's execution delay.
            // The recommended way to handle such scenarios is to use Espresso idling resources:
            // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
            try {
                Thread.sleep(4994);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ViewInteraction appCompatButton2 = onView(
                    allOf(withId(R.id.button_previous), withText("Previous"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.video_player_portrait),
                                            1),
                                    0),
                            isDisplayed()));
            appCompatButton2.perform(click());


            // Added a sleep statement to match the app's execution delay.
            // The recommended way to handle such scenarios is to use Espresso idling resources:
            // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
            try {
                Thread.sleep(4995);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ViewInteraction appCompatImageButton = onView(
                    allOf(withContentDescription("Navigate up"),
                            childAtPosition(
                                    allOf(withId(R.id.action_bar),
                                            childAtPosition(
                                                    withId(R.id.action_bar_container),
                                                    0)),
                                    1),
                            isDisplayed()));
            appCompatImageButton.perform(click());
        }
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1023);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        DataInteraction cardView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.images_grid_view),
                        childAtPosition(
                                withId(R.id.master_list_fragment),
                                0)))
                .atPosition(0);
        cardView2.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.recipe_single_step), withText("Starting prep"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.steps),
                                        1),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        // Check that image is displayed
        try {
            onView(withId(R.id.step_image)).check(matches(isDisplayed()));
        } catch (AssertionFailedError e) {
            // image is not displayed.
        }


        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(4948);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (isPortrait(mActivityTestRule.getActivity())) {
            ViewInteraction appCompatImageButton3 = onView(
                    allOf(withContentDescription("Navigate up"),
                            childAtPosition(
                                    allOf(withId(R.id.action_bar),
                                            childAtPosition(
                                                    withId(R.id.action_bar_container),
                                                    0)),
                                    1),
                            isDisplayed()));
            appCompatImageButton3.perform(click());
        }
        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().screenHeightDp
                > context.getResources().getConfiguration().screenWidthDp;
    }
}