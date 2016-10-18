package com.trubuzz.trubuzz.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.trubuzz.trubuzz.elements.ALogin;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Enumeration;

import dalvik.system.DexFile;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by king on 2016/9/5.
 */
@RunWith(AndroidJUnit4.class)
public class FirstTest {
    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.trubuzz.roy.MainActivity";
    private static Class<?> launcherActivityClass;

    public static Intent intent;

    @Rule
//    public ActivityTestRule<?> matr = new ActivityTestRule(launcherActivityClass,true,true);
    public ActivityTestRule<?> mActivityTestRule = new ActivityTestRule(getLauncherActivityClass(),true,true);

    @Test
    public void testjava8(){
//        for (Method m : this.getClass().getMethods()) {
//            System.out.println("----------------------------------------");
//            System.out.println("   method: " + m.getName());
//            System.out.println("   return: " + m.getReturnType().getName());
//            for (Parameter p : m.getParameters()) {
//                System.out.println("parameter: " + p.getType().getName() + ", " + p.getName());
//            }
//        }
//        say("xiaohua",18);
    }
    public void say(String name ,int age){
    }
//    @Test
    public void te(){
        String name = this.getClass().getName();
        try {
            DexFile dex = new DexFile(mActivityTestRule.getActivity().getApplicationContext().getApplicationInfo().sourceDir);
            DexFile dex1 = new DexFile(getContext().getApplicationInfo().sourceDir);
            System.out.println(dex1.getName());
            Enumeration<String> entries = dex1.entries();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            classLoader.getResourceAsStream("/data/app/com.trubuzz.trubuzz.test-1/base.apk");
            while (entries.hasMoreElements()) {
                String entry = entries.nextElement();
                if (entry.toLowerCase().startsWith(name.toLowerCase())){
                    System.out.println(entry);
                }
            }

            System.out.println("the end ...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   // @Test
    public void testLogin1() throws InterruptedException {
        ALogin aLogin = new ALogin();
        Thread.sleep(2000);
        aLogin.account().perform(replaceText("abc@abc.com"));
        aLogin.password().perform(replaceText("aA123321111"));
        aLogin.submit().perform(click());
        onView(withText("无效的账号或密码"))
                .inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        Context ctx = getContext();
        Resources res = ctx.getResources();
        String s = res.getString(com.trubuzz.trubuzz.test.R.string.accept);
        Log.i("jcd", "testLogin1: "+s);
     //   assertEquals("hello",s);
    }

//    @Test
    public void fir() throws IOException {
//        assertEquals(true,true);
        Context ctx = getContext();
        Activity activity = God.getCurrentActivity(InstrumentationRegistry.getInstrumentation());
       // DoIt.takeScreenshot(uiDevice,new File("libs/aa.png"));

        System.out.println("jj : "+activity.getFilesDir().getCanonicalPath());
        System.out.println("jj : "+ Environment.getDataDirectory().getAbsolutePath());
    }
    public  Class<?> getLauncherActivityClass(){
        try {
            return Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
