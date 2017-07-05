package com.trubuzz.trubuzz.idlingResource;

import android.support.test.espresso.IdlingResource;

import com.trubuzz.trubuzz.shell.beautify.ActivityElement;

import static com.trubuzz.trubuzz.utils.Judge.isExist;

/**
 * Created by king on 2017/7/5.
 * 等待一个ActivityElement 的出现或消失
 *      此处的出现或消失是针对于布局上的 , 而不是简单的 isDisplayed(可见/隐藏)
 */

public class ElementExistIR implements IdlingResource{
    private ActivityElement ae ;
    private boolean wantExist = true;
    private ResourceCallback resourceCallback;
    private boolean isIdle;


    public ElementExistIR(ActivityElement ae) {
        this.ae = ae;
    }

    public ElementExistIR(ActivityElement ae, boolean wantExist) {
        this.ae = ae;
        this.wantExist = wantExist;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle) return true;

        isIdle = isExist(ae);
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
