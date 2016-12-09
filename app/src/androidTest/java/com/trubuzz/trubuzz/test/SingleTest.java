package com.trubuzz.trubuzz.test;

import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.util.Log;
import android.view.View;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.feature.custom.CustomViewAction;
import com.trubuzz.trubuzz.feature.custom.ViewsFinder;
import com.trubuzz.trubuzz.shell.beautify.RecyclerElement;
import com.trubuzz.trubuzz.utils.God;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withCousin;
import static com.trubuzz.trubuzz.feature.custom.CustomRecyclerViewActions.doActions;
import static com.trubuzz.trubuzz.feature.custom.CustomRecyclerViewActions.swipeUpToVisible;
import static com.trubuzz.trubuzz.feature.custom.CustomViewAction.nothing;
import static com.trubuzz.trubuzz.feature.custom.CustomViewAction.swipeDownAs;
import static com.trubuzz.trubuzz.feature.custom.CustomViewAction.swipeLeftAs;
import static com.trubuzz.trubuzz.feature.custom.CustomViewAction.swipeRightAs;
import static com.trubuzz.trubuzz.feature.custom.CustomViewAction.swipeUpAs;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by king on 16/12/6.
 */
@RunWith(AndroidJUnit4.class)
public class SingleTest {
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(God.getFixedClass(AName.MAIN));
    public final String TAG = "jcd_"+this.getClass().getSimpleName();
    Matcher<View> recyclerMatcher;

    UiDevice mDevice;
    String PEOPLE_APP = "People";
    String MY_APP = "XING";
    String userContactName = "Android Tester";

    @Before
    public void setUp() throws Exception{
        Wish.wantLogin();
    }

    @Test
    public void swipeTest(){
//        onView(withText("00330")).perform(click());
//        onView(withText("00700")).perform(new CustomViewAction.SwipeUpToVisible(null ,null));
//        onView(withText("00700")).perform(swipeUp());
//        onView(withText("00700")).perform(swipeUp());
//        onView(withText("00700")).perform(swipeUp());
//        onView(withText("00001")).perform(click());
//        onView(withText("ABAC")).perform(new CustomViewAction.SwipeUpToVisible(Swipe.FAST, Press.FINGER));
        onView(isRoot()).perform(swipeUpAs());
        sleep(3000);
        onView(isRoot()).perform(swipeDownAs(300 ,false));
        sleep(3000);
        onView(isRoot()).perform(swipeLeftAs(300 ,false));
        sleep(3000);
        onView(isRoot()).perform(swipeRightAs(300 ,false));
        onView(isRoot()).perform(
                new CustomViewAction.SwipeAs(Swipe.FAST ,new float[]{213.0f ,1578.356f}
                        ,new float[]{213.0f ,1517.0f} , Press.FINGER.describePrecision())
        );
        sleep(3000);

    }
//    @Test
    public void test() throws UiObjectNotFoundException {
        recyclerMatcher = allOf(withResourceName("recycler"),
                withParent(allOf(
                        withResourceName("refresh"),
                        withCousin(withChild(withText("代码/市值")))
                ))

        );

//        onView(withRecyclerMatcher(recyclerMatcher)
//                .setFindMatcher(withResourceName("percent"))
//                .setPosition(3)
//            .interactionWay()).perform(click());
//        onView(withResourceName("tab")).perform(scrollTo());
//        onView(recyclerMatcher).perform(atPositionAction(31,click()));
//        onView(recyclerMatcher).perform(RecyclerViewActions.actionOnItemAtPosition(31,doActions(swipeUpToVisible() ,nothing(),click())));
//        given(recyclerMatcher).perform(RecyclerViewActions.actionOnItemAtPosition(31, doActions(swipeUpToVisible(), nothing(), click())));
        onView(recyclerMatcher).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("UA")), doActions(swipeUpToVisible(), nothing(), click())));
//        onView(recyclerMatcher).perform(scrollToRecyclerPosition(2));
//        onView(recyclerMatcher).perform(atPositionAction(1,click()));
//        onView(recyclerMatcher).perform(RecyclerViewActions.actionOnItemAtPosition(28,click()));
//        sleep(2000);
        Log.i(TAG, "test: hello");
//        onView(withView(getVisibleView(onView(recyclerMatcher)))).perform(swipeUp());
////        sleep(3000);
        onView(isRoot()).perform(swipeUp());
        onView(new RecyclerElement(recyclerMatcher).atMatcher(allOf(withText("行情") ,isDisplayed()))).perform(
                swipeUpToVisible(),
                click());

        sleep(3000);
    }

//    @Test
    public void t(){
        onView(recyclerMatcher).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()))
        .perform(swipeDown());
        ViewsFinder viewsFinder = new ViewsFinder();
        List<View> recycler = viewsFinder.getViews(withResourceName("recycler"));
        Log.i(TAG, "t: size = " + recycler.size());
        Log.i(TAG, "t: view = "+recycler.get(0).getHandler());
    }
}
