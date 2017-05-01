package at.vocabdevelopment.studymanager;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class NewQuestionInstrumentedTest {

    @Rule
    public ActivityTestRule<NewQuestion> mActivityRule = new ActivityTestRule<>(NewQuestion.class);

    @Test
    public void testToastEmptyQuestionName() throws Exception{
        onView(withId(R.id.editTextQuestionName)).perform(typeText(""));
        onView(withId(R.id.editTextQuestion)).perform(typeText(""));
        onView(withId(R.id.editTextAnswer)).perform(typeText(""));
        onView(withId(R.id.buttonSaveQuestion)).perform(click());
        onView(withText(R.string.toast_empty_question_name))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testToastEmptyChallengeQuestion() throws Exception{
        onView(withId(R.id.editTextQuestionName)).perform(typeText("Question Test Name"));
        onView(withId(R.id.editTextQuestion)).perform(typeText(""));
        onView(withId(R.id.editTextAnswer)).perform(typeText(""));
        onView(withId(R.id.buttonSaveQuestion)).perform(click());
        onView(withText(R.string.toast_empty_challenge_question))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testToastEmptyChallengeAnswer() throws Exception{
        onView(withId(R.id.editTextQuestionName)).perform(typeText("Question Test Name"));
        onView(withId(R.id.editTextQuestion)).perform(typeText("Question Example"));
        onView(withId(R.id.editTextAnswer)).perform(typeText(""));
        onView(withId(R.id.buttonSaveQuestion)).perform(click());
        onView(withText(R.string.toast_empty_challenge_answer))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    //@Test
    //public void testButtons() throws Exception {
    //    onView(withId(R.id.buttonSaveQuestion)).perform(click());
    //}

    //TODO: Test Toggle Button extra, since we need to select a question for this


}
