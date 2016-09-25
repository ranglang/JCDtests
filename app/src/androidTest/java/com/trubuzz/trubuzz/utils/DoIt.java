package com.trubuzz.trubuzz.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;
import android.view.View;

import java.io.File;
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
    private static final String TAG = "jcd_DoIt";

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

    /***********-----------------------------屏幕截图-----------------------------************/

    /**
     *
     * @param activity 必须使用捕获到的当前activity , rule中预设的activity在跨activity时会无效
     * @param imgName
     * @return String file name
     * @deprecated espresso 不能截到toast 。use{@link #takeScreenshot(UiDevice, File)}
     */
    public static String takeScreenshot(Activity activity, String imgName){
        return outPutScreenshot(activity, takeBitmap(activity),imgName);
    }

    /**
     * 截取bitmap
     * @param activity
     * @return
     * @deprecated espresso 不能截到toast 。use{@link #takeScreenshot(UiDevice, File)}
     */
    public static Bitmap takeBitmap(Activity activity){
        View scrView = activity.getWindow().getDecorView().getRootView();
        scrView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(scrView.getDrawingCache());
        scrView.setDrawingCacheEnabled(false);

        Log.i(TAG, "takeBitmap: 截取屏幕");
        return bitmap;
    }

    /**
     * 将bitmap写入磁盘 , 并返回文件名
     * @param activity
     * @param bitmap
     * @param imgName
     * @return
     * @deprecated espresso 不能截到toast 。use{@link #takeScreenshot(UiDevice, File)}
     */
    public static String outPutScreenshot(Activity activity , Bitmap bitmap , String imgName){
        String fileName  = "";
        FileOutputStream out = null;
        try {
            fileName = makeFileName(imgName) + ".png";
            out = activity.openFileOutput(fileName, Activity.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            Log.i("jcd",out.getFD().toString());
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
        return fileName;
    }
    /**
     * 为文件名加上时间戳
     * @param pratName
     * @return
     */
    public static String makeFileName(String pratName){
        String time = God.getDateFormat(new Date(),"yyMMdd_HHmmssSSS", Locale.CHINA);
        pratName = pratName == null ? "" : pratName;
        return "screenshot" + time + "_" + pratName ;
    }

    public static boolean takeScreenshot(UiDevice uiDevice,File file){
        return uiDevice.takeScreenshot(file);
    }

}
