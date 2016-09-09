package com.trubuzz.trubuzz.utils;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;

import java.util.Stack;

/**
 * Created by king on 2016/9/9.
 */
public class DoIt {
    private static java.util.Stack o_idlingResource = null; //某些方法私有的属性

    /**
     * 注册idlingResource 对注册的对象圧栈处理,方便之后注销
     *
     * @param idlingResources
     * @param <T>
     */
    @SafeVarargs
    public static <T extends IdlingResource> void regIdlingResource(T... idlingResources) {
        if (o_idlingResource == null) o_idlingResource = new Stack<IdlingResource>();
        Espresso.registerIdlingResources(idlingResources);
        for (IdlingResource ir : idlingResources) o_idlingResource.push(ir);
    }

    /**
     * 注销一个idlingResources实例,并出栈
     */
    public static void unRegIdlingResource() {
        Espresso.unregisterIdlingResources((IdlingResource) o_idlingResource.pop());
        if (o_idlingResource.empty()) o_idlingResource = null;
    }

    @SafeVarargs
    public static <T extends IdlingResource> void unRegIdlingResource(T... idlingResources) {
        Espresso.unregisterIdlingResources(idlingResources);
        for (IdlingResource ir : idlingResources) {
            o_idlingResource.remove(ir);
        }
        if (o_idlingResource.empty()) o_idlingResource = null;
    }

    /**
     * 注销当前所有的idlingResources实例
     * 此方法需慎用,如果有控制外的实例在栈中,也将被注销.
     */
    public static void unAllRegIdlingResource() {
        while (true) {
            if (o_idlingResource == null) return;
            unRegIdlingResource();
        }
    }


}
