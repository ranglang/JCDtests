package com.trubuzz.trubuzz.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.trubuzz.trubuzz.elements.AForgetPwd;
import com.trubuzz.trubuzz.elements.ALogin;
import com.trubuzz.trubuzz.elements.ASignUp;
import com.trubuzz.trubuzz.idlingResource.SomeActivityIdlingResource;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;

import dalvik.system.DexFile;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static com.trubuzz.trubuzz.constant.AName.WEB_VIEW;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.hasSiblingNoSelf;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.isPassword;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withIndex;
import static com.trubuzz.trubuzz.feature.custom.CustomWebAssert.customWebMatches;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.test.R.string.terms_content;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;
import static com.trubuzz.trubuzz.utils.God.getString;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by king on 2016/9/5.
 */
@RunWith(AndroidJUnit4.class)
public class FirstTest extends BaseTest{
    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.trubuzz.roy.MainActivity";
    private static final String TAG = "jcd_FirstTest";
    private static Class<?> launcherActivityClass;

    public static Intent intent;

    @Rule
//    public ActivityTestRule<?> matr = new ActivityTestRule(launcherActivityClass,true,true);
    public ActivityTestRule<?> mActivityTestRule = new ActivityTestRule(getLauncherActivityClass(),true,true);

//    @Test
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



    @Test
    public void webTableTest(){
//        Object s;
//
//        given(AAsset.ID_TEXT_today_portfolio ).perform(click());
//        DoIt.regIdlingResource(new SomeActivityIdlingResource(NOUNS ,mActivityTestRule.getActivity() , true));
//        s = onWebView()
//                .withElement(findElement(Locator.CSS_SELECTOR, "#main>.footer.base.container-fluid"))
//                .perform(getText())
//                .check(customWebMatches(getText(), containsString(Nouns.today_portfolio_n.getValue())));
//        String except = Nouns.today_portfolio_n.getValue();
//        String got = s.toString();
//        String ex = DoIt.string2ASCII(except);
//        String g = DoIt.string2ASCII(got);
//        Log.w(TAG, "webTableTest: except w = |"+ except + "|");
//        Log.w(TAG, "webTableTest: got    w = |"+ got + "|");
//        Log.e(TAG, "webTableTest: except = |" +  ex + "|");
//        Log.e(TAG, "webTableTest: got    = |" + g + "|" );
//        DoIt.unRegIdlingResource();

        given(ALogin.signUp()).perform(click());
        given(ASignUp.RegEmail.email_input).perform(replaceText("1232142"));
        given(ASignUp.RegEmail.email_terms).perform(click());
        DoIt.regIdlingResource(new SomeActivityIdlingResource(WEB_VIEW,mActivityTestRule.getActivity() ,true));
        onWebView()
                .withElement(findElement(Locator.CSS_SELECTOR , ".terms"))
                .check(customWebMatches(getText() , containsString(getString(terms_content))));
        DoIt.unRegIdlingResource();
        sleep(2000);

    }

//    @Test
    public void devices() throws IllegalAccessException {
//        Log.i(TAG, "devices: MODEL = " + Build.MODEL);
//        Log.i(TAG, "devices: MANUFACTURER = " + Build.MANUFACTURER);
//        Log.i(TAG, "devices: SDK_INT = " + Build.VERSION.SDK_INT);
//        Log.i(TAG, "devices: RELEASE = " + Build.VERSION.RELEASE);
//        Log.i(TAG, "devices: BRAND = " + Build.BRAND);
//        Log.i(TAG, "devices: ID = " + Build.ID);
//        Log.i(TAG, "devices: ID = " + Build.ID);
//        Log.i(TAG, "devices: ID = " + Build.ID);
//        Log.i(TAG, "devices: ID = " + Build.ID);
//        Log.i(TAG, "devices: ID = " + Build.ID);

        Field[] fields = Build.class.getFields();
        for(Field f : fields){
            Log.i(TAG, "devices: "+f.getName() + " = "+ f.get(Build.class));
        }

        Field[] fields_v = Build.VERSION.class.getFields();
        for(Field f : fields_v){
            Log.i(TAG, "devices: "+f.getName() + " = "+ f.get(Build.VERSION.class));
        }
    }


//    @Test
    public void hasSiblingTest(){
        given(ALogin.password()).perform(replaceText("23432312"));
        sleep(2000);
        onView(
                allOf(
                        hasSiblingNoSelf(withResourceName("password")),
                        hasSiblingNoSelf(withText("忘记密码")),
                        withIndex(2)        //with index test

                )
        ).perform(click());
        sleep(2000);
    }

    //@Test
    public void elementTest(){
        given(ALogin.forget_pwd()).perform(click());
        given(AForgetPwd.use_phone_found).perform(click());
        sleep(2000);
    }

//    @Test
    public void isPasswordTest(){
        given(ALogin.account()).perform(replaceText("123321")).check(matches(withText("123321")));
        given(ALogin.password())
                .perform(replaceText("12345678"))
                .check(matches(isPassword()))
                .check(matches(withText("12345678")));
    }

}
