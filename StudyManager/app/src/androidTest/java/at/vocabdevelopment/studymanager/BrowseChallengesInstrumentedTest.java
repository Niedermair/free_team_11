package at.vocabdevelopment.studymanager;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
<<<<<<< HEAD
import static android.support.test.espresso.action.ViewActions.pressBack;
=======
import static android.support.test.espresso.action.ViewActions.pressKey;
>>>>>>> feature_searchLogic
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.object.HasToString.hasToString;

@RunWith(AndroidJUnit4.class)
public class BrowseChallengesInstrumentedTest {

    @Rule
    public ActivityTestRule<BrowseChallenges> mActivityRule = new ActivityTestRule<>(BrowseChallenges.class);

    private String challengeName = "Challenge Name";
    private String searchedChallengeName = "Searched Challenge XYZ";
    private String exampleQuestionName1 = "Question Name 1";
    private String exampleQuestion1 = "Question Example 1";
    private String exampleAnswer1 = "Question Answer 1";
    private String exampleQuestionName2 = "Question Name 2";
    private String exampleQuestion2 = "Question Example 2";
    private String exampleAnswer2 = "Question Answer 2";

    @Test
    public void testToastSelectChallengeWithoutListItem() throws Exception {
        onView(withId(R.id.buttonSelectChallenge)).perform(click());
        onView(withText("Please select a challenge..."))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        Thread.sleep(2500);
    }

    @Test
    public void testSelectChallenge() throws Exception {
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

        mActivityRule.launchActivity(new Intent());

        onData(anything()).inAdapterView(withId(R.id.listViewChallenges))
                .atPosition(0).perform(click());

        onView(withId(R.id.buttonSelectChallenge)).perform(click());

        onView(withId(R.id.textViewSetupChallengeChallengeName)).check(matches(isDisplayed()));

        if(challengeFile.exists()){
            challengeFile.delete();
        }
    }

    @Test
    public void testSelectChallengeInvalid() throws Exception {
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

        mActivityRule.launchActivity(new Intent());

        if(challengeFile.exists()){
            challengeFile.delete();
        }

        onData(anything()).inAdapterView(withId(R.id.listViewChallenges))
                .atPosition(0).perform(click());

        assertNull(mActivityRule.getActivity().selectedChallenge);
    }

    @Test
    public void testAddChallengeRedirect() throws Exception{
        onView(withId(R.id.buttonAddChallenge)).perform(click());

        onView(withId(R.id.editTextChallengeName)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonDeleteChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.listViewQuestions)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonSaveChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonAddQuestion)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonDeleteQuestion)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonEditQuestion)).check(matches(isDisplayed()));
    }

    @Test
    public void testSearchView() throws Exception {
        onView(withId(R.id.searchViewChallenges)).check(matches(isDisplayed()));

        onView(withId(R.id.searchViewChallenges)).perform(typeText("something"), pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.searchViewChallenges)).perform(typeText("something"));
        onView(withText("something")).check(matches(isDisplayed()));
    }



    @Test
    public void testFilteredAndSelectedChallenge() throws Exception {
        File challengeFile = new File(StudyManager.getStorageDir() + File.separator + searchedChallengeName + ".json");
        if(challengeFile.exists()){
            challengeFile.delete();
        }

        Challenge challenge = new Challenge(searchedChallengeName, new ArrayList<Question>());
        Question question1 = new Question(exampleQuestionName1, exampleQuestion1, exampleAnswer1);
        Question question2 = new Question(exampleQuestionName2, exampleQuestion2, exampleAnswer2);
        challenge.addQuestion(question1);
        challenge.addQuestion(question2);
        challenge.constructChallengeFile();

        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.searchViewChallenges)).perform(click(), pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.searchViewChallenges)).perform(typeText("Searched Challenge XYZ"));

        onData(anything()).inAdapterView(withId(R.id.listViewChallenges)).perform(click());

        onView(withId(R.id.buttonSelectChallenge)).perform(click());

        onView(withId(R.id.textViewSetupChallengeChallengeName)).check(matches(withText("Searched Challenge XYZ")));

        if(challengeFile.exists()){
            challengeFile.delete();
        }
    }

<<<<<<< HEAD
    @Test
    public void testBackButton() throws Exception{
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));
    }
=======

>>>>>>> feature_searchLogic
}

