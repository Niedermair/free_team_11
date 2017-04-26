package at.vocabdevelopment.studymanager;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class StartInstrumentedTest {

    @Rule
    public ActivityTestRule<Start> mActivityRule = new ActivityTestRule<>(Start.class);

    @Test
    public void testButtons() throws Exception {
        onView(withId(R.id.buttonContinueChallenge)).perform(click());
        onView(withId(R.id.buttonBrowseChallenges)).perform(click());
    }

    @Test
    public void testBrowseChallengesRedirect() throws  Exception {
        onView(withId(R.id.buttonBrowseChallenges)).perform(click());
        onView(withId(R.id.searchViewChallenges)).check(matches(isDisplayed()));
        onView(withId(R.id.listViewChallanges)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonAddChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonSelectChallenge)).check(matches(isDisplayed()));
    }
}
