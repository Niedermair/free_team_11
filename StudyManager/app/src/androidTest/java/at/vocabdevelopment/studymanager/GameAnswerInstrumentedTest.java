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
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class GameAnswerInstrumentedTest
{
    @Rule
    public ActivityTestRule<GameAnswer> mActivityRule = new ActivityTestRule<>(GameAnswer.class, false, false);

    private String challengeName = "C-Name";
    private String exampleQuestionName = "Q-Name";
    private String exampleQuestion = "Q-Question";
    private String exampleAnswer = "Q-Answer";
    private Game game;

    public void setupIntentData()
    {
        Intent test = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName, exampleQuestion, exampleAnswer);
        challenge.addQuestion(question1);
        game = new Game(challenge, Game.HARD, false);
        test.putExtra("game", game);
        mActivityRule.launchActivity(test);
    }

    public void setupIntentDataMultipleQuestions()
    {
        Intent test = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName, exampleQuestion, exampleAnswer);
        Question question2 = new Question(exampleQuestionName, exampleQuestion, exampleAnswer);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        game = new Game(challenge, Game.HARD, false);
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
    public void testCorrectButtonMultipleQuestions() throws Exception
    {
        setupIntentDataMultipleQuestions();
        onView(withId(R.id.correctBtn)).perform(click());
    }

    @Test
    public void testWrongButton() throws Exception
    {
        setupIntentData();
        onView(withId(R.id.wrongBtn)).perform(click());
    }

    @Test
    public void testWrongButtonMultipleQuestions() throws Exception
    {
        setupIntentDataMultipleQuestions();
        onView(withId(R.id.wrongBtn)).perform(click());
    }

    @Test
    public void testTxtView() throws Exception
    {
        setupIntentData();
        onView(withId(R.id.questionAnswerTxtView)).check(matches(withText(exampleQuestion)));
        onView(withId(R.id.answerAnswerTxtView)).check(matches(withText(exampleAnswer)));
    }

    @Test
    public void testBackButtonApprove() throws Exception{
        setupIntentDataMultipleQuestions();

        String question_name = game.getCurrentQuestionIndex().getQuestion();

        onView(isRoot()).perform(pressBack());

        onView(withText(R.string.dialog_exit_challenge)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_yes)).perform(click());

        onView(withText(R.string.toast_success_game_saved))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).perform(click());
        onView(withId(R.id.questionTxtView)).check(matches(withText(question_name)));
    }

    @Test
    public void testBackButtonDeny() throws Exception{
        setupIntentData();

        onView(isRoot()).perform(pressBack());

        onView(withText(R.string.dialog_exit_challenge)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_no)).perform(click());

        onView(withId(R.id.questionAnswerTxtView)).check(matches(withText(exampleQuestion)));
        onView(withId(R.id.answerAnswerTxtView)).check(matches(withText(exampleAnswer)));
    }
}
