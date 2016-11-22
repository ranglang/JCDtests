package com.trubuzz.trubuzz.test;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.utils.Find;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withView;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;

/**
 * Created by king on 2016/10/19.
 */

public class AssetTest extends BaseTest {

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    @Before
    public void into_asset(){
        Wish.wantBrokerLogin();
    }

    @Test
    public void look(){
        sleep(1000);
//        Matcher m0 = withResourceName("percent");
//        ViewInteraction v = onView(m0);
//        v.perform(click());
//        v.check(matches(isDisplayed()));
        Activity activity = matr.getActivity();
        View view = activity.findViewById(Find.byShortId("change"));
        boolean g = view instanceof ViewGroup;
        boolean l = view instanceof LinearLayout;
//    error    ViewGroup viewGroup = (ViewGroup)view;
//        LinearLayout linearLayout = (LinearLayout)view;
        onView(withView(view)).perform(click());
//        Matcher m = allOf(withResourceName("recycler"),
//                childAtPosition(
//                        allOf(withResourceName("refresh"),
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
//                                        0)),
//                        0),
//                isDisplayed());
//        onData((allOf(is(String.class), containsString("AAPL"))))
//                .inAdapterView(m)
//                .perform(click());
////        onView(withResourceName("percent")).perform(click());


        sleep(1000);
    }

//    private static DataInteraction onRow(String str) {
//        return onData(hasEntry(equalTo(LongListActivity.ROW_TEXT), is(str)));
//    }
}
