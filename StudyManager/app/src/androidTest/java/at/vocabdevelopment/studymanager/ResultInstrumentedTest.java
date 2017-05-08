package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class ResultInstrumentedTest
{
    @Rule
    public ActivityTestRule<Result> mActivityRule = new ActivityTestRule(Result.class, false, false);

    private String challengeName = "C-Name";
    private String exampleQuestionName = "Q-Name";
    private String exampleQuestion = "Q-Question";
    private String exampleAnswer = "Q-Answer";

    public void setupIntentData()
    {
        Intent test = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName, exampleQuestion, exampleAnswer);
        challenge.addQuestion(question1);
        Game game = new Game(challenge);
        test.putExtra("game", game);
        mActivityRule.launchActivity(test);
    }

    @Test
    public void testTextView() throws Exception
    {
        setupIntentData();
        onView(withId(R.id.ResultTxtView)).check(matches(isDisplayed()));
    }

    @Test
    public void testButtons() throws Exception
    {
        setupIntentData();
        onView(withId(R.id.returnToBrowse)).perform(click());
    }
}
