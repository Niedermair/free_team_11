package at.vocabdevelopment.studymanager;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class ResultInstrumentedTest
{
    @Rule
    public ActivityTestRule<Result> mActivityRule = new ActivityTestRule(Result.class, false, false);

    private String challengeName = "C-Name";
    private String exampleQuestionName = "Q-Name";
    private String exampleQuestion = "Q-Question";
    private String exampleAnswer = "Q-Answer";
    private String exampleQuestionName2 = "Q-Name2";
    private String exampleQuestion2 = "Q-Question2";
    private String exampleAnswer2 = "Q-Answer2";

    public void setupIntentData()
    {
        Intent test = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName, exampleQuestion, exampleAnswer);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
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
        onView(withId(R.id.ChartView)).perform(click());
    }

    @Test
    public void testButtons() throws Exception
    {
        setupIntentData();
        onView(withId(R.id.returnToStart)).perform(click());
    }

    @Test
    public void testBackButtonApprove() throws Exception{
        setupIntentData();

        onView(isRoot()).perform(pressBack());

        onView(withText(R.string.dialog_exit_challenge)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_yes)).perform(click());

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
    }

    @Test
    public void testBackButtonDeny() throws Exception{
        setupIntentData();

        onView(isRoot()).perform(pressBack());

        onView(withText(R.string.dialog_exit_challenge)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_no)).perform(click());
    }

    @Test
    public void testBackAndGameFileDeleteFailed() throws Exception{
        setupIntentData();

        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");
        if(gameFile.exists()){
            gameFile.delete();
        }

        onView(isRoot()).perform(pressBack());

        onView(withText(R.string.dialog_exit_challenge)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_yes)).perform(click());

        onView(withText(R.string.toast_error_game_delete))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2500);

        if(gameFile.exists()){
            gameFile.delete();
        }
    }

    @Test
    public void testReturnAndGameFileDeleteFailed() throws Exception{
        setupIntentData();

        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");
        if(gameFile.exists()){
            gameFile.delete();
        }

        onView(withId(R.id.returnToStart)).perform(click());

        onView(withText(R.string.toast_error_game_delete))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2500);

        if(gameFile.exists()){
            gameFile.delete();
        }
    }

    @Test
    public void testReturnToStart() throws Exception{
        setupIntentData();

        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");

        mActivityRule.getActivity().game.constructGameFile();

        onView(withId(R.id.returnToStart)).perform(click());

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2500);

        if(gameFile.exists()){
            gameFile.delete();
        }
    }


    @Test
    public void testExitChallengeSuccess() throws Exception{
        setupIntentData();

        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");

        mActivityRule.getActivity().game.constructGameFile();

        onView(isRoot()).perform(pressBack());

        onView(withText(R.string.dialog_exit_challenge)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_yes)).perform(click());

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2500);

        if(gameFile.exists()){
            gameFile.delete();
        }
    }

}
