package com.trubuzz.trubuzz.test;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.ASettings;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static com.trubuzz.trubuzz.constant.Env.uiDevice;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;

/**
 * Created by king on 16/12/6.
 */
@RunWith(AndroidJUnit4.class)
public class SingleTest {
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    UiDevice mDevice;
    String PEOPLE_APP = "People";
    String MY_APP = "XING";
    String userContactName = "Android Tester";

//    @Before
//    public void setUp() throws Exception{
//        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
//    }

    @Test
    public void test() throws UiObjectNotFoundException {
        ASettings a = new ASettings();
        given(ASettings.left_drawer).perform(click());
        given(a.personal).perform(click());
        given(a.birthday_change_view).perform(click());
        String pak = "com.trubuzz.trubuzz";

//        mDevice.pressHome();
//        sleep(3000);
//        mDevice.findObject(By.res(pak ,"date_picker_year")).click();
//        mDevice.findObject(new UiSelector().resourceId("com.trubuzz.trubuzz:id/date_picker_year")).click();
        sleep(3000);
//        mDevice.findObject(By.descStartsWith("18")).click();
        uiDevice.findObject(new UiSelector().descriptionStartsWith("19")).click();
        sleep(3000);
    }

}
