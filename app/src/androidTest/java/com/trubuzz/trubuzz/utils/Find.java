package com.trubuzz.trubuzz.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by king on 2016/6/29.
 */
public class Find {

    private static Context context = getTargetContext();
    private static Resources resources = context.getResources();


    public static int id(String resourcesId){
        String packageName = resourcesId.split(":")[0];
        String sid = resourcesId.split("/")[1];
        int id = resources.getIdentifier(sid ,"id",packageName);

        if (id != 0){
            return id;
        }
        //若暂时没获取到id,则每隔1秒再获取一次,5次未获取到则直接return 0.
        for(int i = 0 ;i<5 ; i++){
            id = resources.getIdentifier(sid ,"id",packageName);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return id;
    }


    //判断指定的toast是否存在
    public static boolean isToast(ViewInteraction v , ActivityTestRule<?> a){
        try {
            v.inRoot(withDecorView(not(is(a.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
            Log.i("find toast", "ok");
            return true;
        }catch (NoMatchingViewException nsve){
            Log.w("NoMatchingView",nsve);
        }
        return false;
    }


}
