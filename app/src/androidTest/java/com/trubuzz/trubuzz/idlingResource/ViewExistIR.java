package com.trubuzz.trubuzz.idlingResource;

import android.app.Activity;
import android.support.test.espresso.IdlingResource;
import android.view.View;

import com.trubuzz.trubuzz.utils.Find;
import com.trubuzz.trubuzz.utils.God;

import org.hamcrest.Matcher;

import static com.trubuzz.trubuzz.constant.Env.instrumentation;
import static com.trubuzz.trubuzz.utils.Judge.hasMatched;
import static com.trubuzz.trubuzz.utils.Judge.hasView;

/**
 * Created by king on 2017/7/6.
 * 等待一个 View 的出现或消失
 *      此处的出现或消失是针对于布局上的 , 而不是简单的 isDisplayed(可见/隐藏)
 * id 根据 id 在当前 activity 中查找元素布局
 * matcher 在 当前 activity 中查找相匹配的view
 * view and matcher 在给定view的子view中匹配 , 包含自身
 * @deprecated java.lang.RuntimeException: java.util.concurrent.ExecutionException:
 *      java.lang.RuntimeException: This method can not be called from the main application thread
 */

public class ViewExistIR implements IdlingResource{
    private String id;
    private View view;
    private Matcher<View> matcher;

    private boolean wantExist = true;
    private ResourceCallback resourceCallback;
    private boolean isIdle;

    public ViewExistIR(String id) {
        this.id = id;
    }

    public ViewExistIR(String id, boolean wantExist) {
        this.id = id;
        this.wantExist = wantExist;
    }

    public ViewExistIR(View view, Matcher<View> matcher, boolean wantExist) {
        this.view = view;
        this.matcher = matcher;
        this.wantExist = wantExist;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle) return true;


        if (id != null) isIdle = hasView(id);
        else if (view != null && matcher != null) isIdle = hasMatched(view, matcher);
        else if (matcher != null) isIdle = hasMatched(matcher);

        // 如果期望不在布局中出现则对 isIdle取反即可
        if(!wantExist)  isIdle = !isIdle;

        if (isIdle) {
            resourceCallback.onTransitionToIdle();
        }
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}
