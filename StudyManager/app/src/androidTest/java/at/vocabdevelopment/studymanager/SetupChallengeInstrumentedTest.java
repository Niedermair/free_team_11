package at.vocabdevelopment.studymanager;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
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
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

public class SetupChallengeInstrumentedTest {
    @Rule
    public ActivityTestRule<SetupChallenge> mActivityRule = new ActivityTestRule<>(SetupChallenge.class);

    @Test
    public void testButtons() {
        onView(withId(R.id.buttonEdit)).perform(click());
        onView(withId(R.id.buttonActiveDeck)).perform(click());
        onView(withId(R.id.buttonTotalDeck)).perform(click());
        onView(withId(R.id.buttonEasy)).perform(click());
        onView(withId(R.id.buttonMedium)).perform(click());
        onView(withId(R.id.buttonHard)).perform(click());
        onView(withId(R.id.buttonStart)).perform(click());
    }

}
