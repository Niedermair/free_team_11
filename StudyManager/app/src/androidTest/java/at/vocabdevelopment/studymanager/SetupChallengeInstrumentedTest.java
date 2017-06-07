package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import junit.framework.Assert;

import org.hamcrest.CoreMatchers;
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

public class SetupChallengeInstrumentedTest {
    @Rule
    public ActivityTestRule<SetupChallenge> mActivityRule = new ActivityTestRule<>(SetupChallenge.class, false, false);

    private String challengeName = "Challenge Name";
    private String exampleQuestionName1 = "Question Name 1";
    private String exampleQuestion1 = "Question Example 1";
    private String exampleAnswer1 = "Question Answer 1";
    private String exampleQuestionName2 = "Question Name 2";
    private String exampleQuestion2 = "Question Example 2";
    private String exampleAnswer2 = "Question Answer 2";

    public void setupIntentData() {
        Intent data = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        data.putExtra("challenge", challenge);
        mActivityRule.launchActivity(data);
    }

    public void setupIntentDataInactive()
    {
        Intent data = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        question1.setActiveStatus(false);
        question2.setActiveStatus(false);
        data.putExtra("challenge", challenge);
        mActivityRule.launchActivity(data);
    }

    @Test
    public void testLoadChallengeData() throws Exception{
        setupIntentData();
        Assert.assertEquals(mActivityRule.getActivity().challenge.getClass(), Challenge.class);
    }

    @Test
    public void testShowChallengeName() throws Exception{
        setupIntentData();
        onView(withId(R.id.textViewSetupChallengeChallengeName)).check(matches(withText(challengeName)));
    }

    @Test
    public void testLoadChallengeDataMissing() {
        Intent data = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        data.putExtra("challenge_wrong", challenge);
        mActivityRule.launchActivity(data);

        onView(withText(R.string.toast_error_missing_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
    }

    @Test
    public void testMissingIntentExtra() {
        mActivityRule.launchActivity(new Intent());

        onView(withText(R.string.toast_error_missing_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditChallenge(){
        setupIntentData();
        onView(withId(R.id.buttonSetupChallengeEditChallenge)).perform(click());

        onView(withId(R.id.editTextEditChallengeChallengeName)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonEditChallengeDeleteChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.listViewEditChallengeQuestions)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonEditChallengeSaveChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonEditChallengeAddQuestion)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonEditChallengeDeleteQuestion)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonEditChallengeEditQuestion)).check(matches(isDisplayed()));
    }

    @Test
    public void testBackButton() throws Exception{
        setupIntentData();
        onView(isRoot()).perform(pressBack());

        onView(withId(R.id.searchViewChallenges)).check(matches(isDisplayed()));
        onView(withId(R.id.listViewChallenges)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonAddChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonSelectChallenge)).check(matches(isDisplayed()));
    }

    @Test
    public void testButtons() {
        setupIntentData();
        onView(withId(R.id.buttonSetupChallengeEditChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonActiveDeck)).perform(click());
        onView(withId(R.id.buttonTotalDeck)).perform(click());
        onView(withId(R.id.buttonEasy)).perform(click());
        onView(withId(R.id.buttonMedium)).perform(click());
        onView(withId(R.id.buttonHard)).perform(click());
        onView(withId(R.id.buttonStart)).check(matches(isDisplayed()));
    }

    @Test
    public void testNoDifficultySelected() throws Exception {
        setupIntentData();
        onView(withId(R.id.buttonStart)).perform(click());
        onView(withText(R.string.toast_error_difficulty))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testEasyDifficulty() {
        setupIntentData();
        onView(withId(R.id.buttonEasy)).perform(click());
        onView(withId(R.id.buttonStart)).perform(click());
        onView(withId(R.id.questionTxtView)).check(matches(isDisplayed()));
        onView(withId(R.id.showAnswerBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.quitGameBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void testMediumDifficulty() {
        setupIntentData();
        onView(withId(R.id.buttonMedium)).perform(click());
        onView(withId(R.id.buttonStart)).perform(click());
        onView(withId(R.id.questionTxtView)).check(matches(isDisplayed()));
        onView(withId(R.id.showAnswerBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.quitGameBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void testHardDifficulty() {
        setupIntentData();
        onView(withId(R.id.buttonHard)).perform(click());
        onView(withId(R.id.buttonStart)).perform(click());
        onView(withId(R.id.questionTxtView)).check(matches(isDisplayed()));
        onView(withId(R.id.showAnswerBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.quitGameBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void testActiveDeckWithoutActiveQuestions() throws Exception
    {
        setupIntentDataInactive();
        onView(withId(R.id.buttonHard)).perform(click());
        onView(withId(R.id.buttonActiveDeck)).perform(click());
        onView(withId(R.id.buttonStart)).perform(click());
        onView(withText(R.string.toast_no_active_questions))
                .inRoot(withDecorView(CoreMatchers.not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);
    }
}
