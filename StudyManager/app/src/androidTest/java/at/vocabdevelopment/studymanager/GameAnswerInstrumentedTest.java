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

public class GameAnswerInstrumentedTest
{
    @Rule
    public ActivityTestRule<GameAnswer> mActivityRule = new ActivityTestRule<>(GameAnswer.class, false, false);

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
    public void testCorrectButton() throws Exception
    {
        setupIntentData();
        onView(withId(R.id.correctBtn)).perform(click());
    }

    @Test
    public void testWrongButton() throws Exception
    {
        setupIntentData();
        onView(withId(R.id.wrongBtn)).perform(click());
    }

    @Test
    public void testTxtView() throws Exception
    {
        setupIntentData();
        onView(withId(R.id.questionAnswerTxtView)).check(matches(withText("Q-Question")));
        onView(withId(R.id.answerAnswerTxtView)).check(matches(withText("Q-Answer")));
    }
}
