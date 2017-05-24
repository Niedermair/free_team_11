package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
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
        Game game = new Game(challenge, Game.EASY);
        game.incrementWrongCounter();
        game.incrementWrongCounter();
        test.putExtra("game", game);
        mActivityRule.launchActivity(test);
    }

    @Test
    public void testFeedback() throws Exception
    {
        setupIntentData();
        onView(withId(R.id.ResultTxtView)).check(matches(withText("Number of wrong answered questions: 2")));
    }

    @Test
    public void testButtons() throws Exception
    {
        setupIntentData();
        onView(withId(R.id.returnToBrowse)).perform(click());
    }

    @Test
    public void testBackButtonApprove() throws Exception{
        setupIntentData();

        onView(isRoot()).perform(pressBack());

        onView(withText(R.string.dialog_exit_challenge)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_yes)).perform(click());

        onView(withId(R.id.searchViewChallenges)).check(matches(isDisplayed()));
        onView(withId(R.id.listViewChallenges)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonAddChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonSelectChallenge)).check(matches(isDisplayed()));
    }

    @Test
    public void testBackButtonDeny() throws Exception{
        setupIntentData();

        onView(isRoot()).perform(pressBack());

        onView(withText(R.string.dialog_exit_challenge)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_no)).perform(click());

        onView(withId(R.id.ResultTxtView)).check(matches(withText("Number of wrong answered questions: 2")));
    }
}
