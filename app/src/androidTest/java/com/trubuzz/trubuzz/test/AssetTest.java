package com.trubuzz.trubuzz.test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewFinder;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.feature.viewFirm.ViewHandle;
import com.trubuzz.trubuzz.feature.viewFirm.ViewTracer;
import com.trubuzz.trubuzz.shell.AdViewInteraction;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;
import com.trubuzz.trubuzz.utils.Find;
import com.trubuzz.trubuzz.utils.God;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Provider;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withView;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;
import static com.trubuzz.trubuzz.utils.MReflect.getFieldObject;

/**
 * Created by king on 2016/10/19.
 */

public class AssetTest extends BaseTest {

    private static final String TAG = "jcd_AssetTest";
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

   // @Before
    public void into_asset(){
        Wish.wantBrokerLogin();
    }

    @Test
    public void ellook(){
        sleep(1000);
        onView(isRoot()).perform(swipeUp());
        onView(isRoot()).perform(swipeDown());
//        onView(isRoot()).perform(scrollTo());
        List<AdViewInteraction> vl = given(new ActivityElement().setId("percent").setDisplayed(false)).getInteractionList();
        Log.i(TAG, "ellook: view size : "+vl.size());
        for(AdViewInteraction v : vl){
//            if(! Wish.isVisible(v))
                v.perform(swipeUp());


            if(vl.indexOf(v) < 2) {
                v.perform(click());
                Espresso.pressBack();
            }else{
                v.perform(click());
            }
        }
        sleep(1000);
    }
    //@Test
    public void viewLook(){
        sleep(1000);
        View view = this.matr.getActivity().findViewById(Find.byShortId("recycler"));
        if(!(view instanceof ViewGroup)) return;
        ViewGroup viewGroup = (ViewGroup) view;
        int count = viewGroup.getChildCount();
        Log.i(TAG, "viewLook: count = "+count);
        onView(withView(viewGroup.getChildAt(1))).perform(click());

        sleep(2000);
    }

   // @Test
    public void look() throws IllegalAccessException {
        sleep(1000);
        View vi = this.matr.getActivity().findViewById(Find.byShortId("action_menu_item_1"));


        Matcher m0 = withResourceName("percent");
        ViewInteraction v = onView(m0);
        ViewFinder matcher = (ViewFinder) getFieldObject("viewFinder" ,v);
        Matcher<View> viewMatcher = (Matcher<View>) getFieldObject("viewMatcher" ,matcher);
        Provider<View> rootViewProvider = (Provider<View>) getFieldObject("rootViewProvider" ,matcher);
        ViewTracer viewTracer = new ViewTracer(viewMatcher ,rootViewProvider);

        UiController uiController = (UiController) getFieldObject("uiController" ,v);
        FailureHandler failureHandler = (FailureHandler) getFieldObject("failureHandler" ,v);
        Matcher<View> viewMatcher1 = (Matcher<View>) getFieldObject("viewMatcher" ,v);
        Executor mainThreadExecutor = (Executor) getFieldObject("mainThreadExecutor" ,v);
        ViewHandle viewHandle = new ViewHandle(uiController ,mainThreadExecutor ,failureHandler ,viewMatcher1 , viewTracer);



        List<View> a = viewHandle.getTargetViews();

        View view = a.get(6);
        onView(withView(view)).perform(click());
//        v.check(matches(isDisplayed()));
//        Activity activity = matr.getActivity();
//        View view = activity.findViewById(Find.byShortId("change"));
//        boolean g = view instanceof ViewGroup;
//        boolean l = view instanceof LinearLayout;
////    error    ViewGroup viewGroup = (ViewGroup)view;
////        LinearLayout linearLayout = (LinearLayout)view;
//        onView(withView(view)).perform(click());
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
