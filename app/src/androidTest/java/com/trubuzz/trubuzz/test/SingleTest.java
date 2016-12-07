package com.trubuzz.trubuzz.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.util.Log;
import android.view.View;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.feature.custom.ViewsFinder;
import com.trubuzz.trubuzz.utils.God;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withCousin;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withView;
import static com.trubuzz.trubuzz.feature.custom.CustomViewAction.scrollToRecyclerPosition;
import static com.trubuzz.trubuzz.shell.beautify.RecyclerElement.getVisibleViewInteraction;
import static com.trubuzz.trubuzz.shell.beautify.RecyclerElement.withRecyclerMatcher;
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

    UiDevice mDevice;
    String PEOPLE_APP = "People";
    String MY_APP = "XING";
    String userContactName = "Android Tester";

    @Before
    public void setUp() throws Exception{
        Wish.wantLogin();
    }

    @Test
    public void test() throws UiObjectNotFoundException {
        Matcher<View> recyclerMatcher = allOf(withResourceName("recycler"),
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
        onView(recyclerMatcher).perform(scrollToRecyclerPosition(31));
        sleep(2000);
        Log.i(TAG, "test: hello");
        onView(withView(getVisibleViewInteraction(onView(recyclerMatcher)))).perform(swipeUp());
//        sleep(3000);
        onView(withRecyclerMatcher(recyclerMatcher).atPosition(31)).perform(
//                pause(1000),
//                scrollToRecyclerPosition(6),pause(1000),
                click());

//        sleep(3000);
    }

//    @Test
    public void t(){
        ViewsFinder viewsFinder = new ViewsFinder();
        List<View> recycler = viewsFinder.getViews(withResourceName("recycler"));
        Log.i(TAG, "t: size = " + recycler.size());
        Log.i(TAG, "t: view = "+recycler.get(0).getHandler());
    }
}
