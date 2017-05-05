package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;


@RunWith(AndroidJUnit4.class)
public class EditChallengeInstrumentedTest {

    private String challengeName = "Challenge Name";
    private String exampleQuestionName1 = "Question Name 1";
    private String exampleQuestion1 = "Question Example1";
    private String exampleAnswer1 = "Question Answer1";
    private String exampleQuestionName2 = "Question Name 2";
    private String exampleQuestion2 = "Question Example2";
    private String exampleAnswer2 = "Question Answer2";

    @Rule
    public ActivityTestRule<EditChallenge> mActivityRule = new ActivityTestRule<>(EditChallenge.class, false, false);

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

    @Test
    public void testLoadChallengeName() throws Exception{
        setupIntentData();
        onView(withId(R.id.editTextEditChallengeChallengeName)).check(matches(withText(challengeName)));
    }

    @Test
    public void testLoadChallengeQuestions() throws Exception{
        setupIntentData();

        onView(withId(R.id.listViewEditChallengeQuestions)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(0).check(matches(withText(exampleQuestionName1)));
        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(1).check(matches(withText(exampleQuestionName2)));
    }

    @Test
    public void testMissingIntentExtra() throws Exception{
        Intent data = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        data.putExtra("challenge_wrong", challenge);
        mActivityRule.launchActivity(data);

        onView(withText(R.string.toast_error_missing_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(1500);
    }

    @Test
    public void testMissingIntent() throws Exception{
        mActivityRule.launchActivity(new Intent());

        onView(withText(R.string.toast_error_missing_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(1500);
    }

    @Test
    public void testAddQuestion() throws Exception{
        setupIntentData();
        onView(withId(R.id.buttonEditChallengeAddQuestion)).perform(click());

        onView(withId(R.id.editTextQuestionName)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextQuestion)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextAnswer)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonSaveQuestion)).check(matches(isDisplayed()));
    }

    @Test
    public void testSaveChallenge() throws Exception{
        setupIntentData();
        onView(withId(R.id.buttonEditChallengeSaveChallenge)).perform(click());
    }

    @Test
    public void testDeleteChallenge() throws Exception{
        setupIntentData();
        onView(withId(R.id.buttonEditChallengeDeleteChallenge)).perform(click());
    }

    @Test
    public void testEditQuestion() throws Exception{
        setupIntentData();
        onView(withId(R.id.buttonEditChallengeEditQuestion)).perform(click());
    }

    @Test
    public void testDeleteQuestion() throws Exception{
        setupIntentData();
        onView(withId(R.id.buttonEditChallengeDeleteQuestion)).perform(click());
    }

    @Test
    public void testToggleStatus() throws Exception{
        setupIntentData();
        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).perform(click());
    }
}
