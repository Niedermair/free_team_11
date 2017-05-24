package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.support.test.espresso.core.deps.guava.base.Strings;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

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

public class GameQuestionInstrumentedTest
{
    @Rule
    public ActivityTestRule<GameQuestion> mActivityRule = new ActivityTestRule<>(GameQuestion.class, false, false);

    private String challengeName = "C-Name";
    private String exampleQuestionName = "Q-Name";
    private String exampleQuestion = "Q-Question";
    private String exampleAnswer = "Q-Answer";

    public void setupIntentData() {
        Intent test = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName, exampleQuestion, exampleAnswer);
        challenge.addQuestion(question1);
        Game game = new Game(challenge);
        test.putExtra("game", game);
        mActivityRule.launchActivity(test);
    }

    @Test
    public void testQuitButton() throws Exception
    {
        setupIntentData();
        onView(withId(R.id.quitGameBtn)).perform(click());
    }

    @Test
    public void testAnswerButton() throws Exception
    {
        setupIntentData();
        onView(withId(R.id.showAnswerBtn)).perform(click());
    }

    @Test
    public void testTxtView() throws Exception

    {
        setupIntentData();
        onView(withId(R.id.questionTxtView)).check(matches(withText(exampleQuestion)));
    }

    @Test
    public void testQuitGameApprove() throws Exception{
        setupIntentData();

        onView(withId(R.id.quitGameBtn)).perform(click());

        onView(withText(R.string.dialog_exit_challenge)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_yes)).perform(click());

        onView(withId(R.id.searchViewChallenges)).check(matches(isDisplayed()));
        onView(withId(R.id.listViewChallenges)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonAddChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonSelectChallenge)).check(matches(isDisplayed()));
    }

    @Test
    public void testQuitGameDeny() throws Exception{
        setupIntentData();

        onView(withId(R.id.quitGameBtn)).perform(click());

        onView(withText(R.string.dialog_exit_challenge)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_no)).perform(click());

        onView(withId(R.id.questionTxtView)).check(matches(withText(exampleQuestion)));
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

        onView(withId(R.id.questionTxtView)).check(matches(withText(exampleQuestion)));
    }
}