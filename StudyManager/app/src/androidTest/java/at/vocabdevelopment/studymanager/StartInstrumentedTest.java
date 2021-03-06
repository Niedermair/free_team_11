package at.vocabdevelopment.studymanager;

import android.os.Build;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.io.File;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class StartInstrumentedTest {

    @Rule
    public ActivityTestRule<Start> mActivityRule = new ActivityTestRule<>(Start.class);

    @Before
    public void resetPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm reset-permissions");
        }
    }

    @Test
    public void testContinueChallengeDenyPermissions() throws Exception{

        denyPermissionsIfNeeded();
        onView(withId(R.id.buttonContinueChallenge)).check(matches(not(isEnabled())));
    }

    @Test
    public void testContinueChallengeAllowPermissions() throws Exception{
        allowPermissionsIfNeeded();
        onView(withId(R.id.buttonContinueChallenge)).check(matches(isEnabled()));
        onView(withId(R.id.buttonContinueChallenge)).perform(click());
    }

    @Test
    public void testBrowseChallengesRedirectDenyPermissions() throws  Exception {
        denyPermissionsIfNeeded();
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(not(isEnabled())));
    }

    @Test
    public void testBrowseChallengesRedirectAllowPermissions() throws  Exception {
        allowPermissionsIfNeeded();
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isEnabled()));
        onView(withId(R.id.buttonBrowseChallenges)).perform(click());

        onView(withId(R.id.searchViewChallenges)).check(matches(isDisplayed()));
        onView(withId(R.id.listViewChallenges)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonAddChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonSelectChallenge)).check(matches(isDisplayed()));
    }

    @Test
    public void testDenyPermissionInformation() throws Exception{
        denyPermissionsIfNeeded();
        onView(withId(R.id.textViewPermissions)).check(matches(isDisplayed()));
    }

    @Test
    public void testAllowPermissionInformation() throws Exception{
        allowPermissionsIfNeeded();
        onView(withId(R.id.textViewPermissions)).check(matches(not(isDisplayed())));
    }

    @Test(expected= NoActivityResumedException.class)
    public void testBackButton() throws Exception{
        allowPermissionsIfNeeded();
        onView(isRoot()).perform(pressBack());
        mActivityRule.getActivity();
    }

    @Test(expected= NoActivityResumedException.class)
    public void testBackButton2() throws Exception{
        denyPermissionsIfNeeded();
        onView(isRoot()).perform(pressBack());
        mActivityRule.getActivity();
    }


    @Test
    public void permissionWriteDenied(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant android.permission.WRITE_EXTERNAL_STORAGE");
        }
        allowPermissionsIfNeeded();
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isEnabled()));
    }

    @Test
    public void permissionReadDenied(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant android.permission.READ_EXTERNAL_STORAGE");
        }
        allowPermissionsIfNeeded();
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isEnabled()));
    }

    @Test
    public void testNoSavedGame() throws Exception {

        allowPermissionsIfNeeded();

        File gameFile = new File(StudyManager.getCurrentGameDir() + File.separator +
                "currentGame.json");
        if(gameFile.exists()){
            gameFile.delete();
        }

        onView(withId(R.id.buttonContinueChallenge)).perform(click());

        onView(withText(R.string.toast_error_no_saved_game))
                .inRoot(withDecorView(CoreMatchers.not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonContinueChallenge)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBrowseChallenges)).check(matches(isDisplayed()));

        Thread.sleep(2500);

        if(gameFile.exists()){
            gameFile.delete();
        }
    }


    private void allowPermissionsIfNeeded(){
        if (Build.VERSION.SDK_INT >= 23) {
            UiDevice device = UiDevice.getInstance(getInstrumentation());
            UiObject allowPermissions = device.findObject(new UiSelector().text("Allow"));
            if (allowPermissions.exists()) {
                try {
                    allowPermissions.click();
                } catch (UiObjectNotFoundException e) {
                    Log.e("app", "No permission popup available.");
                }
            }
        }
    }

    private void denyPermissionsIfNeeded()  {
        if (Build.VERSION.SDK_INT >= 23) {
            UiDevice device = UiDevice.getInstance(getInstrumentation());
            UiObject denyPermissions = device.findObject(new UiSelector().text("Deny"));
            if (denyPermissions.exists()) {
                try {
                    denyPermissions.click();
                } catch (UiObjectNotFoundException e) {
                    Log.e("app", "No permission popup available.");
                }
            }
        }
    }
}
