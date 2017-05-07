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
import static android.support.test.espresso.action.ViewActions.clearText;
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
public class EditQuestionInstrumentedTest {

    private String challengeName = "Challenge Name";
    private String exampleQuestionName1 = "Question Name 1";
    private String exampleQuestion1 = "Question Example 1";
    private String exampleAnswer1 = "Question Answer 1";
    private String exampleQuestionName2 = "Question Name 2";
    private String exampleQuestion2 = "Question Example 2";
    private String exampleAnswer2 = "Question Answer 2";

    private String exampleQuestionNameChanged = "Question Name New";
    private String exampleQuestionChanged = "Question Example New";
    private String exampleAnswerChanged = "Question Answer New";


    @Rule
    public ActivityTestRule<EditQuestion> mActivityRule = new ActivityTestRule<>(EditQuestion.class, false, false);

    public void setupIntentData(String fromActivity, int questionPosition) {
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

        data.putExtra("questionPosition", questionPosition);


        mActivityRule.launchActivity(data);
    }

    @Test
    public void testLoadChallengeData() throws Exception{
        setupIntentData("newChallenge", 0);
        assertEquals(mActivityRule.getActivity().challenge.getClass(), Challenge.class);
    }

    @Test
    public void testLoadChallengeDataMissing() throws Exception{
        Intent data = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        data.putExtra("challenge_wrong", challenge);
        data.putExtra("fromActivity", "newChallenge");
        data.putExtra("questionPosition", 0);
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
    public void testLoadFromActivityDataNewChallenge() throws Exception{
        setupIntentData("newChallenge", 0);
        assertEquals(mActivityRule.getActivity().fromActivity, "newChallenge");
    }

    @Test
    public void testLoadFromActivityDataEditChallenge() throws Exception{
        setupIntentData("editChallenge", 0);
        assertEquals(mActivityRule.getActivity().fromActivity, "editChallenge");
    }

    @Test
    public void testLoadFromActivityDataMissing() throws Exception{
        setupIntentData("", 0);
        onView(withText(R.string.toast_error_missing_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testLoadValidQuestionPositionData() throws Exception{
        setupIntentData("newChallenge", 0);
        assertEquals(mActivityRule.getActivity().questionPosition, 0);
    }

    @Test
    public void testLoadValidQuestionPositionData2() throws Exception{
        setupIntentData("newChallenge", 1);
        assertEquals(mActivityRule.getActivity().questionPosition, 1);
    }

    @Test
    public void testLoadInvalidQuestionPositionData() throws Exception{
        setupIntentData("newChallenge", 2);
        onView(withText(R.string.toast_error_corrupt_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testLoadInvalidQuestionPositionData2() throws Exception{
        setupIntentData("newChallenge", -1);
        onView(withText(R.string.toast_error_corrupt_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testLoadInvalidQuestionPositionData3() throws Exception{
        Intent data = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        data.putExtra("challenge", challenge);
        data.putExtra("fromActivity", "newChallenge");
        data.putExtra("questionPosition", 0);
        mActivityRule.launchActivity(data);

        onView(withText(R.string.toast_error_corrupt_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testLoadQuestionPositionDataParseError() throws Exception{
        Intent data = new Intent();

        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        data.putExtra("challenge", challenge);
        data.putExtra("fromActivity", "newChallenge");
        data.putExtra("questionPosition", "NO_NUMBER");
        mActivityRule.launchActivity(data);

        onView(withText(R.string.toast_error_corrupt_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testLoadQuestionPositionDataMissing() throws Exception{
        Intent data = new Intent();

        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        data.putExtra("challenge", challenge);
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
    public void testLoadCurrentQuestion() throws Exception{
        setupIntentData("newChallenge", 0);
        onView(withId(R.id.editTextQuestionNameEdit)).check(matches(withText(exampleQuestionName1)));
        onView(withId(R.id.editTextAnswerEdit)).check(matches(withText(exampleAnswer1)));
        onView(withId(R.id.editTextQuestionEdit)).check(matches(withText(exampleQuestion1)));
    }

    @Test
    public void testToastEmptyQuestionName() throws Exception{
        setupIntentData("newChallenge", 0);
        onView(withId(R.id.editTextQuestionNameEdit)).perform(clearText(), typeText(" "));
        onView(withId(R.id.buttonSaveQuestionEdit)).perform(click());
        onView(withText(R.string.toast_empty_question_name))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testToastEmptyChallengeQuestion() throws Exception{
        setupIntentData("newChallenge", 0);
        onView(withId(R.id.editTextQuestionEdit)).perform(clearText(), typeText(" "));
        onView(withId(R.id.buttonSaveQuestionEdit)).perform(click());
        onView(withText(R.string.toast_empty_challenge_question))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testToastEmptyChallengeAnswer() throws Exception{
        setupIntentData("newChallenge", 0);
        onView(withId(R.id.editTextAnswerEdit)).perform(clearText(), typeText(" "));
        onView(withId(R.id.buttonSaveQuestionEdit)).perform(click());
        onView(withText(R.string.toast_empty_challenge_answer))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testSaveEditQuestionNewChallenge() throws Exception{
        setupIntentData("newChallenge", 0);
        onView(withId(R.id.buttonSaveQuestionEdit)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listViewQuestions))
                .atPosition(0).check(matches(withText(exampleQuestionName1)));
        onData(anything()).inAdapterView(withId(R.id.listViewQuestions))
                .atPosition(1).check(matches(withText(exampleQuestionName2)));
    }

    @Test
    public void testSaveEditQuestionNewChallenge2() throws Exception{
        setupIntentData("newChallenge", 0);
        onView(withId(R.id.editTextQuestionNameEdit)).perform(clearText(), typeText(exampleQuestionNameChanged));
        onView(withId(R.id.editTextQuestionEdit)).perform(clearText(), typeText(exampleQuestionChanged));
        onView(withId(R.id.editTextAnswerEdit)).perform(clearText(), typeText(exampleAnswerChanged));
        onView(withId(R.id.buttonSaveQuestionEdit)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listViewQuestions))
                .atPosition(0).check(matches(withText(exampleQuestionNameChanged)));
        onData(anything()).inAdapterView(withId(R.id.listViewQuestions))
                .atPosition(1).check(matches(withText(exampleQuestionName2)));
    }

    @Test
    public void testSaveEditQuestionNewChallenge3() throws Exception{
        setupIntentData("newChallenge", 1);
        onView(withId(R.id.editTextQuestionNameEdit)).perform(clearText(), typeText(exampleQuestionNameChanged));
        onView(withId(R.id.editTextQuestionEdit)).perform(clearText(), typeText(exampleQuestionChanged));
        onView(withId(R.id.editTextAnswerEdit)).perform(clearText(), typeText(exampleAnswerChanged));
        onView(withId(R.id.buttonSaveQuestionEdit)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listViewQuestions))
                .atPosition(0).check(matches(withText(exampleQuestionName1)));
        onData(anything()).inAdapterView(withId(R.id.listViewQuestions))
                .atPosition(1).check(matches(withText(exampleQuestionNameChanged)));
    }

    @Test
    public void testSaveEditQuestionEditChallenge() throws Exception{
        setupIntentData("editChallenge", 0);
        onView(withId(R.id.buttonSaveQuestionEdit)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(0).check(matches(withText(exampleQuestionName1)));
        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(1).check(matches(withText(exampleQuestionName2)));
    }

    @Test
    public void testSaveEditQuestionEditChallenge2() throws Exception{
        setupIntentData("editChallenge", 0);
        onView(withId(R.id.editTextQuestionNameEdit)).perform(clearText(), typeText(exampleQuestionNameChanged));
        onView(withId(R.id.editTextQuestionEdit)).perform(clearText(), typeText(exampleQuestionChanged));
        onView(withId(R.id.editTextAnswerEdit)).perform(clearText(), typeText(exampleAnswerChanged));
        onView(withId(R.id.buttonSaveQuestionEdit)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(0).check(matches(withText(exampleQuestionNameChanged)));
        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(1).check(matches(withText(exampleQuestionName2)));
    }

    @Test
    public void testSaveEditQuestionEditChallenge3() throws Exception{
        setupIntentData("editChallenge", 1);
        onView(withId(R.id.editTextQuestionNameEdit)).perform(clearText(), typeText(exampleQuestionNameChanged));
        onView(withId(R.id.editTextQuestionEdit)).perform(clearText(), typeText(exampleQuestionChanged));
        onView(withId(R.id.editTextAnswerEdit)).perform(clearText(), typeText(exampleAnswerChanged));
        onView(withId(R.id.buttonSaveQuestionEdit)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(0).check(matches(withText(exampleQuestionName1)));
        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(1).check(matches(withText(exampleQuestionNameChanged)));
    }

    @Test(expected=Exception.class)
    public void testSaveNewQuestionInvalidFromActivity() throws Exception{
        setupIntentData("fromIllegalActivity", 0);
        onView(withId(R.id.editTextQuestionName)).perform(typeText(exampleQuestionNameChanged));
        onView(withId(R.id.editTextQuestion)).perform(typeText(exampleQuestionChanged));
        onView(withId(R.id.editTextAnswer)).perform(typeText(exampleAnswerChanged));
        onView(withId(R.id.buttonSaveQuestion)).perform(click());
    }

    @Test(expected=Exception.class)
    public void testSaveNewQuestionInvalidFromActivity2() throws Exception{
        setupIntentData("fromIllegalActivity", 1);
        onView(withId(R.id.buttonSaveQuestion)).perform(click());
    }

}
