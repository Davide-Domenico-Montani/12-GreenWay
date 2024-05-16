package it.unimib.greenway.ui.main;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unimib.greenway.R;
import it.unimib.greenway.ui.welcome.WelcomeActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ChallengeFragmentTest {

    @Rule
    public ActivityScenarioRule<WelcomeActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(WelcomeActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Before
    public  void Login() throws InterruptedException {

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.button_login), withText(R.string.login),
                        childAtPosition(
                                allOf(withId(R.id.button_layout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.editTextEmail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayoutEmail),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("ciccio@gmail.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.editTextPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayoutPassword),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("Ciccio123"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.buttonLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0),
                                2),
                        isDisplayed()));
        materialButton2.perform(click());
        Thread.sleep(2000);

    }

    @Test
    public void challengeFriendsTabsTest() throws InterruptedException {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.item_challenge), withContentDescription("Sfide"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());
        Thread.sleep(2000);

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerViewChallengeFriends),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));
        Thread.sleep(2000);

        ViewInteraction tabView = onView(
                allOf(withContentDescription("Amici"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabLayoutChallenge),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());
        Thread.sleep(2000);

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recyclerViewChallengeFriends),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        recyclerView2.check(matches(isDisplayed()));

        ViewInteraction tabView2 = onView(
                allOf(withContentDescription("Sfide"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabLayoutChallenge),
                                        0),
                                0),
                        isDisplayed()));
        tabView2.perform(click());
        Thread.sleep(2000);

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.recyclerViewChallengeFriends),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        recyclerView3.check(matches(isDisplayed()));

    }
    @Test
    public void friendChallengeTest() throws InterruptedException {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.item_challenge), withContentDescription("Sfide"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction tabView = onView(
                allOf(withContentDescription("Amici"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabLayoutChallenge),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());
        Thread.sleep(2000);

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerViewChallengeFriends),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        Thread.sleep(2000);

        ViewInteraction frameLayout = onView(
                allOf(withParent(allOf(withId(R.id.fragment_container_main),
                                withParent(withId(R.id.fragment_container_main)))),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));
        Thread.sleep(2000);

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recyclerViewFriendChallenge),
                        withParent(withParent(withId(R.id.fragment_container_main))),
                        isDisplayed()));
        recyclerView2.check(matches(isDisplayed()));
        Thread.sleep(2000);

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.backButton),
                        childAtPosition(
                                allOf(withId(R.id.toolbarTop),
                                        childAtPosition(
                                                withId(R.id.appBarLayoutTop),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());
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
}
