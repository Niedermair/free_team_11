package at.sw2017.calculator;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CalculatorInstrumentedTest {

    @Rule
    public ActivityTestRule<Calculator> mActivityRule = new ActivityTestRule<>(Calculator.class);

    @Test
    public void testButtons() throws Exception {
        for(int i = 0; i <= 9; i++){
            onView(withText(Integer.toString(i))).perform(click());
        }
        onView(withText("+")).perform(click());
        onView(withText("-")).perform(click());
        onView(withText("*")).perform(click());
        onView(withText("/")).perform(click());

        onView(withText("=")).perform(click());
        onView(withText("C")).perform(click());
    }

    @Test
    public void testInputField(){
        for(int i = 9; i >= 0; i--){
            onView(withText(Integer.toString(i))).perform(click());
        }
        onView(withText("9876543210")).check(matches(isDisplayed()));
    }

    @Test
    public void testClearButton(){
        onView(withText("3")).perform(click());
        onView(withText("C")).perform(click());

        onView(withId(R.id.numberView)).check(matches(withText("0")));
    }

    @Test
    public void testMultiplicationEmptySecondNumber(){
        onView(withText("3")).perform(click());
        onView(withText("*")).perform(click());
        onView(withText("=")).perform(click());

        onView(withId(R.id.numberView)).check(matches(withText("0")));
    }

    @Test
    public void testAdditionEmptySecondNumber(){
        onView(withText("3")).perform(click());
        onView(withText("+")).perform(click());
        onView(withText("=")).perform(click());

        onView(withId(R.id.numberView)).check(matches(withText("3")));
    }

    @Test
    public void testSubtractionEmptySecondNumber(){
        onView(withText("3")).perform(click());
        onView(withText("-")).perform(click());
        onView(withText("=")).perform(click());

        onView(withId(R.id.numberView)).check(matches(withText("3")));
    }

    @Test
    public void testEqualEmptySecondNumber(){
        onView(withText("3")).perform(click());
        onView(withText("=")).perform(click());

        onView(withId(R.id.numberView)).check(matches(withText("3")));
    }
}
