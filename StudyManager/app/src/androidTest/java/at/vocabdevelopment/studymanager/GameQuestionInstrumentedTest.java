package at.vocabdevelopment.studymanager;

import android.support.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class GameQuestionInstrumentedTest
{
    @Rule
    public ActivityTestRule<GameQuestion> mActivityRule = new ActivityTestRule<>(GameQuestion.class);

    @Test
    public void testButtons() throws Exception
    {
        onView(withId(R.id.showAnswerBtn)).perform(click());
        onView(withId(R.id.quitGameBtn)).perform(click());
    }

    @Test
    public void testTxtView() throws Exception
    {
        onView(withId(R.id.questionTxtView)).check(matches(isDisplayed()));
    }
}