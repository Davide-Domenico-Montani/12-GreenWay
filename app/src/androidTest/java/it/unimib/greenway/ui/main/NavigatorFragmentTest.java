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
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unimib.greenway.R;
import it.unimib.greenway.ui.welcome.WelcomeActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NavigatorFragmentTest {

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
    public void correcAddresstSearch() throws InterruptedException {
        Thread.sleep(2000);
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.item_nav), withContentDescription("Navigatore"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_input),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_fragment),
                                        childAtPosition(
                                                withClassName(is("androidx.cardview.widget.CardView")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar),
                        childAtPosition(
                                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar_container),
                                        childAtPosition(
                                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("viale zara"), closeSoftKeyboard());
        Thread.sleep(2000);

        ViewInteraction recyclerView = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_list),
                        childAtPosition(
                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                3)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        Thread.sleep(2000);

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_input),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_fragment2),
                                        childAtPosition(
                                                withClassName(is("androidx.cardview.widget.CardView")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar),
                        childAtPosition(
                                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar_container),
                                        childAtPosition(
                                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("seregno"), closeSoftKeyboard());
        Thread.sleep(2000);
        ViewInteraction recyclerView2 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_list),
                        childAtPosition(
                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                3)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));
        Thread.sleep(2000);

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.buttonFind), withText("Cerca"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_main),
                                        0),
                                2),
                        isDisplayed()));
        materialButton3.perform(click());

    }


    @Test
    public void errorAddressSearch() throws InterruptedException {
        Thread.sleep(2000);
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.item_nav), withContentDescription("Navigatore"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_input),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_fragment),
                                        childAtPosition(
                                                withClassName(is("androidx.cardview.widget.CardView")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar),
                        childAtPosition(
                                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar_container),
                                        childAtPosition(
                                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("viale zara"), closeSoftKeyboard());

        ViewInteraction recyclerView = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_list),
                        childAtPosition(
                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                3)));
        Thread.sleep(2000);

        recyclerView.perform(actionOnItemAtPosition(0, click()));
        Thread.sleep(2000);

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.buttonFind), withText("Cerca"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_main),
                                        0),
                                2),
                        isDisplayed()));
        materialButton2.perform(click());
        Thread.sleep(2000);

        ViewInteraction frameLayout = onView(
                allOf(withParent(allOf(withId(R.id.fragment_container_main),
                                withParent(withId(R.id.fragment_container_main)))),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));

    }
    @Test
    public void navigatorRoutesRecyclerViewsTest() throws InterruptedException {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.item_nav), withContentDescription("Navigatore"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_input),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_fragment),
                                        childAtPosition(
                                                withClassName(is("androidx.cardview.widget.CardView")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(click());
        Thread.sleep(2000);

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar),
                        childAtPosition(
                                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar_container),
                                        childAtPosition(
                                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("Seregno"), closeSoftKeyboard());
        Thread.sleep(2000);

        ViewInteraction recyclerView = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_list),
                        childAtPosition(
                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                3)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        Thread.sleep(2000);

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_input),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_fragment2),
                                        childAtPosition(
                                                withClassName(is("androidx.cardview.widget.CardView")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(click());
        Thread.sleep(2000);

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar),
                        childAtPosition(
                                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar_container),
                                        childAtPosition(
                                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar),
                        childAtPosition(
                                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar_container),
                                        childAtPosition(
                                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("Viale zara"), closeSoftKeyboard());
        Thread.sleep(2000);

        ViewInteraction recyclerView2 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_list),
                        childAtPosition(
                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                3)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.buttonFind), withText("Cerca"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_main),
                                        0),
                                2),
                        isDisplayed()));
        materialButton3.perform(click());
        Thread.sleep(5000);

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.recyclerViewRoutes),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        recyclerView3.check(matches(isDisplayed()));

        ViewInteraction tabView = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabLayout),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());
        Thread.sleep(2000);

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.recyclerViewRoutes),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        recyclerView4.check(matches(isDisplayed()));
        Thread.sleep(2000);

        ViewInteraction tabView2 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabLayout),
                                        0),
                                2),
                        isDisplayed()));
        tabView2.perform(click());
        Thread.sleep(2000);

        ViewInteraction recyclerView5 = onView(
                allOf(withId(R.id.recyclerViewRoutes),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        recyclerView5.check(matches(isDisplayed()));
    }

    /*
    @Test
    public void navigatorRouteViewTest() throws InterruptedException {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.item_nav), withContentDescription("Navigatore"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_input),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_fragment),
                                        childAtPosition(
                                                withClassName(is("androidx.cardview.widget.CardView")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(click());
        Thread.sleep(2000);

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar),
                        childAtPosition(
                                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar_container),
                                        childAtPosition(
                                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("Seregno"), closeSoftKeyboard());
        Thread.sleep(2000);

        ViewInteraction recyclerView = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_list),
                        childAtPosition(
                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                3)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_input),
                        childAtPosition(
                                allOf(withId(R.id.autocomplete_fragment2),
                                        childAtPosition(
                                                withClassName(is("androidx.cardview.widget.CardView")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(click());
        Thread.sleep(2000);

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar),
                        childAtPosition(
                                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_search_bar_container),
                                        childAtPosition(
                                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("Viale Zara"), closeSoftKeyboard());
        Thread.sleep(2000);

        ViewInteraction recyclerView2 = onView(
                allOf(withId(com.google.android.libraries.places.R.id.places_autocomplete_list),
                        childAtPosition(
                                withId(com.google.android.libraries.places.R.id.places_autocomplete_content),
                                3)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));
        Thread.sleep(2000);

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.buttonFind), withText("Cerca"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_main),
                                        0),
                                2),
                        isDisplayed()));
        materialButton3.perform(click());
        Thread.sleep(4000);

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.buttonNavigate), withText("Apri Mappa"),
                        childAtPosition(
                                allOf(withId(R.id.relativeLayoutCardRoute),
                                        childAtPosition(
                                                withId(R.id.route_card),
                                                0)),
                                2),
                        isDisplayed()));
        materialButton4.perform(click());
        Thread.sleep(2000);

        ViewInteraction view = onView(
                allOf(withContentDescription("Google Maps"),
                        withParent(withParent(withId(R.id.id_map))),
                        isDisplayed()));
        view.check(matches(isDisplayed()));

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
        Thread.sleep(2000);

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.buttonSelect), withText("Seleziona"),
                        childAtPosition(
                                allOf(withId(R.id.relativeLayoutCardRoute),
                                        childAtPosition(
                                                withId(R.id.route_card),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction frameLayout = onView(
                allOf(withParent(allOf(withId(R.id.fragment_container_main),
                                withParent(withId(R.id.fragment_container_main)))),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));
    }

     */

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
    public static Matcher<View> firstChildOf(final Matcher<View> parentMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with first child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && ((ViewGroup) parent).getChildAt(0).equals(view);
            }
        };
    }
}
