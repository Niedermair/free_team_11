package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Button;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class NewQuestionInstrumentedTest {

    private String challengeName = "Challenge Name";
    private String exampleQuestionName1 = "Question Name 1";
    private String exampleQuestion1 = "Question Example 1";
    private String exampleAnswer1 = "Question Answer 1";
    private String exampleQuestionName2 = "Question Name 2";
    private String exampleQuestion2 = "Question Example 2";
    private String exampleAnswer2 = "Question Answer 2";

    private String newExampleQuestionName = "Question New";
    private String newExampleQuestion = "Question Example New";
    private String newExampleAnswer = "Question Answer New";

    @Rule
    public ActivityTestRule<NewQuestion> mActivityRule = new ActivityTestRule<>(NewQuestion.class, false, false);

    public void setupIntentData(String fromActivity) {
        Intent data = new Intent();

        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        data.putExtra("challenge", challenge);

        if(fromActivity.length() > 0){
            data.putExtra("fromActivity", fromActivity);
        }

        mActivityRule.launchActivity(data);
    }

    @Test
    public void testLoadFromActivityDataNewChallenge() throws Exception{
        setupIntentData("newChallenge");
        assertEquals(mActivityRule.getActivity().fromActivity, "newChallenge");
    }

    @Test
    public void testLoadFromActivityDataEditChallenge() throws Exception{
        setupIntentData("editChallenge");
        assertEquals(mActivityRule.getActivity().fromActivity, "editChallenge");
    }

    @Test
    public void testLoadFromActivityDataMissing() throws Exception{
        setupIntentData("");
        onView(withText(R.string.toast_error_missing_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testLoadChallengeData() throws Exception{
        setupIntentData("newChallenge");
        assertEquals(mActivityRule.getActivity().challenge.getClass(), Challenge.class);
    }

    @Test
    public void testLoadChallengeDataMissing() throws Exception{
        Intent data = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        data.putExtra("challenge_wrong", challenge);
        data.putExtra("fromActivity", "newChallenge");
        mActivityRule.launchActivity(data);

        onView(withText(R.string.toast_error_missing_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testMissingIntentExtra() throws Exception{
        mActivityRule.launchActivity(new Intent());

        onView(withText(R.string.toast_error_missing_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testToastEmptyQuestionName() throws Exception{
        setupIntentData("newChallenge");
        onView(withId(R.id.editTextQuestionName)).perform(typeText(" "));
        onView(withId(R.id.editTextQuestion)).perform(typeText(" "));
        onView(withId(R.id.editTextAnswer)).perform(typeText(" "));
        onView(withId(R.id.buttonSaveQuestion)).perform(click());
        onView(withText(R.string.toast_empty_question_name))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testToastEmptyChallengeQuestion() throws Exception{
        setupIntentData("newChallenge");
        onView(withId(R.id.editTextQuestionName)).perform(typeText(newExampleQuestionName));
        onView(withId(R.id.editTextQuestion)).perform(typeText(" "));
        onView(withId(R.id.editTextAnswer)).perform(typeText(" "));
        onView(withId(R.id.buttonSaveQuestion)).perform(click());
        onView(withText(R.string.toast_empty_challenge_question))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testToastEmptyChallengeAnswer() throws Exception{
        setupIntentData("newChallenge");
        onView(withId(R.id.editTextQuestionName)).perform(typeText(newExampleQuestionName));
        onView(withId(R.id.editTextQuestion)).perform(typeText(newExampleQuestion));
        onView(withId(R.id.editTextAnswer)).perform(typeText(" "));
        onView(withId(R.id.buttonSaveQuestion)).perform(click());
        onView(withText(R.string.toast_empty_challenge_answer))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testSaveNewQuestionNewChallenge() throws Exception{
        setupIntentData("newChallenge");
        onView(withId(R.id.editTextQuestionName)).perform(typeText(newExampleQuestionName));
        onView(withId(R.id.editTextQuestion)).perform(typeText(newExampleQuestion));
        onView(withId(R.id.editTextAnswer)).perform(typeText(newExampleAnswer));
        onView(withId(R.id.buttonSaveQuestion)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listViewQuestions))
                .atPosition(2).check(matches(withText(newExampleQuestionName)));
    }

    @Test
    public void testSaveNewQuestionEditChallenge() throws Exception{
        setupIntentData("editChallenge");
        onView(withId(R.id.editTextQuestionName)).perform(typeText(newExampleQuestionName));
        onView(withId(R.id.editTextQuestion)).perform(typeText(newExampleQuestion));
        onView(withId(R.id.editTextAnswer)).perform(typeText(newExampleAnswer));
        onView(withId(R.id.buttonSaveQuestion)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(2).check(matches(withText(newExampleQuestionName)));
    }

    @Test(expected=Exception.class)
    public void testSaveNewQuestionInvalidFromActivity() throws Exception{
        setupIntentData("fromIllegalActivity");
        onView(withId(R.id.editTextQuestionName)).perform(typeText(newExampleQuestionName));
        onView(withId(R.id.editTextQuestion)).perform(typeText(newExampleQuestion));
        onView(withId(R.id.editTextAnswer)).perform(typeText(newExampleAnswer));
        onView(withId(R.id.buttonSaveQuestion)).perform(click());
    }
}
