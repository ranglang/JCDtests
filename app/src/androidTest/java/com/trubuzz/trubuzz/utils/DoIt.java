package com.trubuzz.trubuzz.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;
import android.view.View;

import com.trubuzz.trubuzz.constant.Env;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
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
            fileName = makeImageName(imgName) ;
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
    public static String makeImageName(String pratName){
        String time = God.getDateFormat(new Date(),"yyMMdd_HHmmssSSS", Locale.CHINA);
        pratName = pratName == null ? "" : pratName;
        return "jcd_" + time + "_" + pratName;
    }

    public static String writeFileData( String data , String fileName){
        BufferedWriter writer = null;
        File file = null;
        try {
            file = new File(Env.filesDir + makeImageName("json_"+ fileName) + ".json");
            if (file.exists()){
                file.createNewFile();
            }
            writer = new BufferedWriter(new FileWriter(file ,true));
            writer.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getAbsolutePath();
    }

    public static String takeScreenshot(UiDevice uiDevice,File file){
        uiDevice.takeScreenshot(file);
        return file.getAbsolutePath();
    }
    public static String takeScreenshot(UiDevice uiDevice,File file ,float scale, int quality){
        uiDevice.takeScreenshot(file, scale,  quality);
        return file.getAbsolutePath();
    }
    public static String takeScreenshot(UiDevice uiDevice,String imageName){
        String fileAbsolutePath = Env.filesDir+ makeImageName( "ps_"+ imageName) + ".png";
        uiDevice.takeScreenshot(new File(fileAbsolutePath));
        return fileAbsolutePath;
    }


    /*************************/
    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*****************************/
    public static boolean delFile(String filePath){
        try {
            if(filePath == null || filePath.isEmpty()) return true;

            new File(filePath).delete();
            Log.i(TAG, "delFile:  删除临时文件 :"+filePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /***********************************/
    public static Map enum2map(Enum a){
        return null;
    }

    /********************************/

    public static String string2ASCII(String string){
        String newString = "";
        char[] chars = string.toCharArray();
        for(char c: chars){
            newString = newString + Integer.valueOf(c) + ",";
        }
        return newString;
    }
}
