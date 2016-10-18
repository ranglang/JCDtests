package com.trubuzz.trubuzz.feature;

import android.os.IBinder;
import android.support.test.espresso.Root;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by king on 2016/9/23.
 */

public class CustomMatcher {

    private static final String TAG = "jcd_CustomMatcher";

    /**
     * 匹配 toast
     * @return
     */
    public static Matcher<Root> withToast(){
        return new TypeSafeMatcher<Root>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("is toast");
            }

            @Override
            protected boolean matchesSafely(Root root) {
                int type = root.getWindowLayoutParams().get().type;
                if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                    IBinder windowToken = root.getDecorView().getWindowToken();
                    IBinder appToken = root.getDecorView().getApplicationWindowToken();
                    if (windowToken == appToken) {
                        //means this window isn't contained by any other windows.
                        return true;
                    }
                }
                return false;
            }
        };
    }

    /**
     * 通过孩子元素的位置匹配
     * @param parentMatcher
     * @param position
     * @return
     */
    public static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    /**
     * 通过祖先元素匹配 ( 看起来无法单独使用 )
     * @param ancestorMatcher
     * @return
     */
    public static Matcher<View> withAncestor(final Matcher<View> ancestorMatcher){
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with ancestor that  ");
                ancestorMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(View view) {
                ViewParent viewParent = view.getParent();
                while (true){
                    if(ancestorMatcher.matches(viewParent)){
                        return true;
                    }else{
                        viewParent = viewParent.getParent();
                        if(viewParent == null) return false;
                    }
                }
            }
        };
    }

    /**
     * 通过叔父元素的位置匹配 ( 位置相对于祖父而言 )
     * @param uncleMatcher
     * @param position
     * @return
     */
    public static Matcher<View> withUnclePosition(final Matcher<View> uncleMatcher , final int position){
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with uncle position that ");
                uncleMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(View view) {
                ViewGroup viewGroup = (ViewGroup) view.getParent().getParent();
                View uncleView = viewGroup.getChildAt(position);
                return uncleMatcher.matches(uncleView);
            }
        };
    }

    /**
     * 使用 uncle view 相对于 自身父级元素的位置来匹配
     * 在父元素上一位置 为 -1 , 下一行则为 1 ,一次类推
     * @param uncleMatcher
     * @param relativePosition
     * @return
     */
    public static Matcher<View> withUncleRelativePosition(final Matcher<View> uncleMatcher , final int relativePosition){
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with uncle relative position that ");
                uncleMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(View view) {
                ViewGroup viewGroup = (ViewGroup) view.getParent().getParent();
                int selfIndex = 0;
                int count = viewGroup.getChildCount();
                for( ; selfIndex<count; selfIndex++){
                    View cv = viewGroup.getChildAt(selfIndex);
                    if (view.getParent().equals(cv)){
                        Log.i(TAG, "matchesSafely: count = "+ count);
                        Log.i(TAG, "matchesSafely:  view index = "+ selfIndex);
                        break;
                    }
                }
                View uncleView = viewGroup.getChildAt(relativePosition + selfIndex);
                return uncleMatcher.matches(uncleView);
            }
        };
    }
}
