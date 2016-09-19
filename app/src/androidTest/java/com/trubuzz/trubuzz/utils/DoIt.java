package com.trubuzz.trubuzz.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.view.View;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;

/**
 * Created by king on 2016/9/9.
 * 功能: 做一些事情
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

    /***********************/

    /**
     *
     * @param activity 必须使用捕获到的当前activity , rule中预设的activity在跨activity时会无效
     * @param imgName
     */
    public static void takeScreenshot(Activity activity, String imgName){
        View scrView = activity.getWindow().getDecorView().getRootView();
        scrView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(scrView.getDrawingCache());
        scrView.setDrawingCacheEnabled(false);

        FileOutputStream out = null;
        try {
            out = activity.openFileOutput(makeFileName(imgName) + ".png", Activity.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    public static String makeFileName(String pratName){
        String time = God.getDateFormat(new Date(),"yyyy-MM-dd_HH:mm:ss:SSS", Locale.CHINA);
        pratName = pratName == null ? "" : pratName;
        return time + "_" + pratName ;
    }
}
