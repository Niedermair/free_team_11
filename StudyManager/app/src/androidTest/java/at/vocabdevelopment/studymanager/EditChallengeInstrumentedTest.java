package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;


@RunWith(AndroidJUnit4.class)
public class EditChallengeInstrumentedTest {

    private String challengeName = "Challenge Name";
    private String exampleQuestionName1 = "Question Name 1";
    private String exampleQuestion1 = "Question Example 1";
    private String exampleAnswer1 = "Question Answer 1";
    private String exampleQuestionName2 = "Question Name 2";
    private String exampleQuestion2 = "Question Example 2";
    private String exampleAnswer2 = "Question Answer 2";

    private String newChallengeName = "Challenge Name New";

    @Rule
    public ActivityTestRule<EditChallenge> mActivityRule = new ActivityTestRule<>(EditChallenge.class, false, false);

    public void setupIntentData() {
        Intent data = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        question2.setActiveStatus(false);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        data.putExtra("challenge", challenge);
        data.putExtra("originalChallenge", challenge);
        mActivityRule.launchActivity(data);
    }

    public void setupIntentData2() {
        Intent data = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        question2.setActiveStatus(false);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        data.putExtra("challenge", challenge);
        data.putExtra("originalChallenge", challenge);
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
    public void testLoadChallengeDataMissing() throws Exception{
        Intent data = new Intent();
        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        data.putExtra("challenge_wrong", challenge);
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
    public void testSelectQuestion() throws Exception{
        setupIntentData();

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(0).perform(click());

        assertNotNull(mActivityRule.getActivity().selectedQuestionPos);
        assertEquals(mActivityRule.getActivity().selectedQuestionPos, 0);
    }

    @Test
    public void testSelectQuestionError() throws Exception{
        setupIntentData();

        assertEquals(mActivityRule.getActivity().selectedQuestionPos, -1);
    }

    @Test
    public void testEditQuestion() throws Exception{
        setupIntentData();

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(1).perform(click());

        assertNotNull(mActivityRule.getActivity().selectedQuestionPos);
        assertEquals(mActivityRule.getActivity().selectedQuestionPos, 1);

        onView(withId(R.id.buttonEditChallengeEditQuestion)).perform(click());

        onView(withId(R.id.editTextQuestionNameEdit)).check(matches(withText(exampleQuestionName2)));
        onView(withId(R.id.editTextQuestionEdit)).check(matches(withText(exampleQuestion2)));
        onView(withId(R.id.editTextAnswerEdit)).check(matches(withText(exampleAnswer2)));
        onView(withId(R.id.buttonSaveQuestionEdit)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditQuestionError() throws Exception{
        setupIntentData();

        assertEquals(mActivityRule.getActivity().selectedQuestionPos, -1);

        onView(withId(R.id.buttonEditChallengeEditQuestion)).perform(click());

        onView(withText(R.string.toast_select_a_question))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testChangeChallengeNameAndEditQuestion() throws Exception{
        setupIntentData();

        onView(withId(R.id.editTextEditChallengeChallengeName)).perform(clearText(), typeText(newChallengeName));

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(1).perform(click());

        assertNotNull(mActivityRule.getActivity().selectedQuestionPos);
        assertEquals(mActivityRule.getActivity().selectedQuestionPos, 1);

        onView(withId(R.id.buttonEditChallengeEditQuestion)).perform(click());

        onView(withId(R.id.editTextQuestionNameEdit)).check(matches(withText(exampleQuestionName2)));
        onView(withId(R.id.editTextQuestionEdit)).check(matches(withText(exampleQuestion2)));
        onView(withId(R.id.editTextAnswerEdit)).check(matches(withText(exampleAnswer2)));
        onView(withId(R.id.buttonSaveQuestionEdit)).perform(click());

        onView(withId(R.id.editTextEditChallengeChallengeName)).check(matches(withText(newChallengeName)));
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
    public void testDeleteQuestionApprove() throws Exception{
        setupIntentData();

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(1).perform(click());

        assertNotNull(mActivityRule.getActivity().selectedQuestionPos);
        assertEquals(mActivityRule.getActivity().selectedQuestionPos, 1);

        onView(withId(R.id.buttonEditChallengeDeleteQuestion)).perform(click());

        onView(withText(R.string.dialog_delete_question)).check(matches(isDisplayed()));

        onView(withText(R.string.dialog_yes)).perform(click());

        assertEquals(mActivityRule.getActivity().selectedQuestionPos, -1);
        assertEquals(mActivityRule.getActivity().challenge.getQuestionList().size(), 1);
        assertEquals(mActivityRule.getActivity().challenge.getQuestionList().get(0).getName(), exampleQuestionName1);
    }

    @Test
    public void testDeleteQuestionDeny() throws Exception{
        setupIntentData();

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(1).perform(click());

        assertNotNull(mActivityRule.getActivity().selectedQuestionPos);
        assertEquals(mActivityRule.getActivity().selectedQuestionPos, 1);

        onView(withId(R.id.buttonEditChallengeDeleteQuestion)).perform(click());

        onView(withText(R.string.dialog_delete_question)).check(matches(isDisplayed()));

        onView(withText(R.string.dialog_no)).perform(click());

        assertEquals(mActivityRule.getActivity().selectedQuestionPos, -1);
        assertEquals(mActivityRule.getActivity().challenge.getQuestionList().size(), 2);
        assertEquals(mActivityRule.getActivity().challenge.getQuestionList().get(0).getName(), exampleQuestionName1);
        assertEquals(mActivityRule.getActivity().challenge.getQuestionList().get(1).getName(), exampleQuestionName2);
    }

    @Test
    public void testDeleteQuestionError() throws Exception{
        setupIntentData();

        assertEquals(mActivityRule.getActivity().selectedQuestionPos, -1);

        onView(withId(R.id.buttonEditChallengeDeleteQuestion)).perform(click());

        onView(withText(R.string.toast_select_a_question))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testToastSaveEditChallengeWithoutName() throws Exception{
        setupIntentData();
        onView(withId(R.id.editTextEditChallengeChallengeName)).perform(clearText(), typeText(" "));
        onView(withId(R.id.buttonEditChallengeSaveChallenge)).perform(click());
        onView(withText(R.string.toast_empty_challenge_name))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testToastSaveEditChallengeWithoutQuestions() throws Exception{
        setupIntentData();

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions)).atPosition(0).perform(click());
        onView(withId(R.id.buttonEditChallengeDeleteQuestion)).perform(click());
        onView(withText(R.string.dialog_delete_question)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_yes)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions)).atPosition(0).perform(click());
        onView(withId(R.id.buttonEditChallengeDeleteQuestion)).perform(click());
        onView(withText(R.string.dialog_delete_question)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_yes)).perform(click());

        onView(withId(R.id.buttonEditChallengeSaveChallenge)).perform(click());
        onView(withText(R.string.toast_one_question))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);
    }


    @Test
    public void testSaveEditChallengeSuccess() throws Exception{
        setupIntentData();

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + challengeName + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);

        challenge.constructChallengeFile();

        onView(withId(R.id.buttonEditChallengeSaveChallenge)).perform(click());

        onView(withText(R.string.toast_success_challenge_saved))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);

        onView(withId(R.id.textViewSetupChallengeChallengeName)).check(matches(withText(challengeName)));

        if(challengeFile.exists()){
            challengeFile.delete();
        }
    }

    @Test
    public void testSaveEditChallengeSuccess2() throws Exception{
        setupIntentData();

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + challengeName + ".json");
        File newChallengeFile = new File(StudyManager.getStorageDir() + File.separator + newChallengeName + ".json");
        if(newChallengeFile.exists()){
            newChallengeFile.delete();
        }
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);

        challenge.constructChallengeFile();

        onView(withId(R.id.editTextEditChallengeChallengeName)).perform(clearText(), typeText(newChallengeName));

        onView(withId(R.id.buttonEditChallengeSaveChallenge)).perform(click());

        onView(withText(R.string.toast_success_challenge_saved))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);

        onView(withId(R.id.textViewSetupChallengeChallengeName)).check(matches(withText(newChallengeName)));

        if(newChallengeFile.exists()){
            newChallengeFile.delete();
        }
    }

    @Test
    public void testSaveEditChallengeFail() throws Exception{
        setupIntentData();

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + challengeName + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        onView(withId(R.id.buttonEditChallengeSaveChallenge)).perform(click());

        onView(withText(R.string.toast_error_save_data))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
        Thread.sleep(2500);
    }


    @Test
    public void testDeleteEditChallenge() throws Exception{
        setupIntentData();

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + challengeName + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);

        challenge.constructChallengeFile();

        onView(withId(R.id.buttonEditChallengeSaveChallenge)).perform(click());

        onView(withText(R.string.toast_success_challenge_saved))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);

        onView(withId(R.id.buttonSetupChallengeEditChallenge)).perform(click());
        onView(withId(R.id.buttonEditChallengeDeleteChallenge)).perform(click());
        onView(withText(R.string.dialog_delete_challenge)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_yes)).perform(click());

        onView(withText(R.string.toast_success_challenge_deleted))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.searchViewChallenges)).check(matches(isDisplayed()));
        onView(withId(R.id.listViewChallenges)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonAddChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonSelectChallenge)).check(matches(isDisplayed()));
        Thread.sleep(2500);

    }

    @Test
    public void testDeleteEditChallengeError() throws Exception{
        setupIntentData();

        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + challengeName + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        Challenge challenge = new Challenge(challengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);

        challenge.constructChallengeFile();
        challenge.deleteChallengeFile();

        onView(withId(R.id.buttonEditChallengeDeleteChallenge)).perform(click());
        onView(withText(R.string.dialog_delete_challenge)).check(matches(isDisplayed()));
        onView(withText(R.string.dialog_yes)).perform(click());

        onView(withText(R.string.toast_error_challenge_delete))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.searchViewChallenges)).check(matches(isDisplayed()));
        onView(withId(R.id.listViewChallenges)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonAddChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonSelectChallenge)).check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testDeleteChallengeDeny() throws Exception{
        setupIntentData();

        onView(withId(R.id.buttonEditChallengeDeleteChallenge)).perform(click());

        onView(withText(R.string.dialog_delete_challenge)).check(matches(isDisplayed()));

        onView(withText(R.string.dialog_no)).perform(click());

        onView(withId(R.id.editTextEditChallengeChallengeName)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonEditChallengeDeleteChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.listViewEditChallengeQuestions)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonEditChallengeSaveChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonEditChallengeAddQuestion)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonEditChallengeDeleteQuestion)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonEditChallengeEditQuestion)).check(matches(isDisplayed()));

    }

    @Test
    public void testQuestionStatusToggleButtonEnabled() throws Exception{
        setupIntentData();

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(1).perform(click());

        assertNotNull(mActivityRule.getActivity().selectedQuestionPos);
        assertEquals(mActivityRule.getActivity().selectedQuestionPos, 1);

        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).check(matches(isEnabled()));
    }

    @Test
    public void testQuestionStatusToggleButtonDisabled() throws Exception{
        setupIntentData();

        assertNotNull(mActivityRule.getActivity().selectedQuestionPos);
        assertEquals(mActivityRule.getActivity().selectedQuestionPos, -1);

        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).check(matches(not(isEnabled())));
    }

    @Test
    public void testQuestionStatusToggleButtonDisplayStatus() throws Exception{
        setupIntentData();

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(0).perform(click());

        assertNotNull(mActivityRule.getActivity().selectedQuestionPos);
        assertEquals(mActivityRule.getActivity().selectedQuestionPos, 0);

        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).check(matches(isEnabled()));
        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).check(matches(isChecked()));
    }

    @Test
    public void testQuestionStatusToggleButtonDisplayStatus2() throws Exception{
        setupIntentData();

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(1).perform(click());

        assertNotNull(mActivityRule.getActivity().selectedQuestionPos);
        assertEquals(mActivityRule.getActivity().selectedQuestionPos, 1);

        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).check(matches(isEnabled()));
        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).check(matches(not(isChecked())));
    }

    @Test
    public void testQuestionToggleStatus() throws Exception{
        setupIntentData();

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(0).perform(click());

        assertNotNull(mActivityRule.getActivity().selectedQuestionPos);
        assertEquals(mActivityRule.getActivity().selectedQuestionPos, 0);

        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).check(matches(isEnabled()));
        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).check(matches(isChecked()));
        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).perform(click());
        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).check(matches(isEnabled()));
        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).check(matches(not(isChecked())));

        assertFalse(mActivityRule.getActivity().challenge.getQuestionList().get(0).getActiveStatus());
    }

    @Test
    public void testQuestionToggleStatus2() throws Exception{
        setupIntentData();

        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(1).perform(click());

        assertNotNull(mActivityRule.getActivity().selectedQuestionPos);
        assertEquals(mActivityRule.getActivity().selectedQuestionPos, 1);

        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).check(matches(isEnabled()));
        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).check(matches(not(isChecked())));
        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).perform(click());
        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).check(matches(isEnabled()));
        onView(withId(R.id.toggleButtonEditChallengeQuestionStatus)).check(matches(isChecked()));

        assertTrue(mActivityRule.getActivity().challenge.getQuestionList().get(1).getActiveStatus());
    }

    @Test
    public void testLoadOldChallengeIfNotSaved() throws Exception{
        setupIntentData2();

        onView(withId(R.id.editTextEditChallengeChallengeName)).perform(clearText(), typeText(newChallengeName));
        onData(anything()).inAdapterView(withId(R.id.listViewEditChallengeQuestions))
                .atPosition(1).perform(click());

        onView(withId(R.id.buttonEditChallengeEditQuestion)).perform(click());
        onView(withId(R.id.buttonSaveQuestionEdit)).perform(click());

        onView(withId(R.id.editTextEditChallengeChallengeName)).check(matches(withText(newChallengeName)));

        onView(isRoot()).perform(pressBack());
        onView(isRoot()).perform(pressBack());

        onView(withId(R.id.textViewSetupChallengeChallengeName)).check(matches(withText(challengeName)));
    }

    @Test
    public void testBackButton() throws Exception{
        setupIntentData();

        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.textViewSetupChallengeChallengeName)).check(matches(withText(challengeName)));
    }

    @Test
    public void testBackButton2() throws Exception{
        setupIntentData();

        onView(withId(R.id.editTextEditChallengeChallengeName)).perform(clearText(), typeText(newChallengeName));
        onView(isRoot()).perform(pressBack());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.textViewSetupChallengeChallengeName)).check(matches(withText(challengeName)));
    }
}
