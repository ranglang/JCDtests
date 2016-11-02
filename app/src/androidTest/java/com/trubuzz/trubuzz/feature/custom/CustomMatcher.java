package com.trubuzz.trubuzz.feature.custom;

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

import static android.support.test.internal.util.Checks.checkNotNull;

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
     * 通过叔父查找
     * @param uncleMatcher
     * @return
     */
    public static Matcher<View> withUncle(final Matcher<View> uncleMatcher){
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with uncle:");
                uncleMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(View view) {
                ViewParent grandParent = view.getParent().getParent();
                if (!(grandParent instanceof ViewGroup)) {
                    return false;
                }
                ViewGroup grandParentGroup = (ViewGroup) grandParent;
                for(int i=0;i<grandParentGroup.getChildCount();i++){
                    if(uncleMatcher.matches(grandParentGroup.getChildAt(i)))
                        return true;
                }
                return false;
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
    /**
     * 通过index定位
     * @param index
     * @return
     */
    public static Matcher<View> withIndex(final int index){
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with index: "+index);
            }

            @Override
            protected boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                if (!(parent instanceof ViewGroup)) {
                    return false;
                }
                ViewGroup parentGroup = (ViewGroup) parent;
                return view.equals(parentGroup.getChildAt(index));
            }
        };
    }

    /**
     * 匹配指定文本
     * @param str
     * @return
     */
    public static Matcher<String> thisString(String str){
        return new TypeSafeMatcher<String>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("text must be this : "+str);
            }

            @Override
            protected boolean matchesSafely(String item) {
                return item.equals(str);
            }
        };
    }

    public static Matcher<String> singleSpaceContains(String str){
        return new TypeSafeMatcher<String>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("text must contains this : "+str);
            }

            @Override
            protected boolean matchesSafely(String item) {
                return item.replaceAll("\\x20+", " ").contains(str);
            }
        };
    }
    /**
     * 匹配指定对象
     * @param obj
     * @return
     */
    public static Matcher<Object> thisObject(Object obj) {
        return new TypeSafeMatcher<Object>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Object must be this : "+obj);
            }

            @Override
            protected boolean matchesSafely(Object item) {
                return item.equals(obj);
            }
        };
    }


    /**
     * 基于指定的兄弟姐妹匹配, 不包含自身.
     * {@link android.support.test.espresso.matcher.ViewMatchers#hasSibling } 则是包含自身的实现
     * @param siblingMatcher
     * @return
     */
    public static Matcher<View> hasSiblingNoSelf(final Matcher<View> siblingMatcher){
        checkNotNull(siblingMatcher);
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("has sibling: ");
                siblingMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                if (!(parent instanceof ViewGroup)) {
                    return false;
                }
                ViewGroup parentGroup = (ViewGroup) parent;
                for (int i = 0; i < parentGroup.getChildCount(); i++) {
                    View siblingView = parentGroup.getChildAt(i);
                    if (siblingMatcher.matches(siblingView) && !siblingView.equals(view)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
