package at.vocabdevelopment.studymanager;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class NewChallengeInstrumentedTest {

    @Rule
    public ActivityTestRule<NewChallenge> mActivityRule = new ActivityTestRule<>(NewChallenge.class);

    @Test
    public void testButtons() throws Exception {
        onView(withId(R.id.buttonDeleteChallenge)).perform(click());
        onView(withId(R.id.buttonSaveChallenge)).perform(click());
        onView(withId(R.id.buttonEditQuestion)).perform(click());
        onView(withId(R.id.buttonAddQuestion)).perform(click());
        onView(withId(R.id.buttonDeleteQuestion)).perform(click());
    }

    //TODO: Test Toggle Button extra, since we need to select a question for this


}
