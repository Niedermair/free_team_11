package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;

import org.junit.Before;
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
public class NewChallengeInstrumentedTest {

    @Rule
    public ActivityTestRule<NewChallenge> mActivityRule = new ActivityTestRule<>(NewChallenge.class);

    @Test
    public void testAddQuestion() throws Exception{
        onView(withId(R.id.buttonAddQuestion)).perform(click());

        onView(withId(R.id.editTextQuestionName)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextQuestion)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextAnswer)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonSaveQuestion)).check(matches(isDisplayed()));
    }

    @Test
    public void testToastSaveChallengeWithoutName() throws Exception{
        onView(withId(R.id.editTextChallengeName)).perform(typeText(" "));
        onView(withId(R.id.buttonSaveChallenge)).perform(click());
        onView(withText(R.string.toast_empty_challenge_name))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testToastSaveChallengeWithoutQuestions() throws Exception{
        onView(withId(R.id.editTextChallengeName)).perform(typeText("New Challenge"));
        onView(withId(R.id.buttonSaveChallenge)).perform(click());
        onView(withText(R.string.toast_one_question))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    //TODO: still to implement....

    @Test
    public void testDeleteChallenge() throws Exception{
        onView(withId(R.id.buttonDeleteChallenge)).perform(click());
    }

    @Test
    public void testEditQuestion() throws Exception{
        onView(withId(R.id.buttonEditQuestion)).perform(click());
    }

    @Test
    public void testDeleteQuestion() throws Exception{
        onView(withId(R.id.buttonDeleteQuestion)).perform(click());
    }

    @Test
    public void testToggleStatus() throws Exception{
        onView(withId(R.id.toggleButtonQuestionStatus)).perform(click());
    }
}
