package com.trubuzz.trubuzz.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.ASettings;
import com.trubuzz.trubuzz.elements.Global;
import com.trubuzz.trubuzz.shell.Uncalibrated;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.constant.Config.cacheHeadImage;
import static com.trubuzz.trubuzz.constant.Env.uiDevice;
import static com.trubuzz.trubuzz.constant.ToastInfo.status_success_toast;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.hasDrawable;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.thisObject;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getText;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.test.R.string.female;
import static com.trubuzz.trubuzz.test.R.string.male;
import static com.trubuzz.trubuzz.utils.DoIt.createWatermark;
import static com.trubuzz.trubuzz.utils.DoIt.outPutScreenshot;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;
import static com.trubuzz.trubuzz.utils.God.getResources;
import static com.trubuzz.trubuzz.utils.God.getString;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasToString;

/**
 * Created by king on 16/11/29.
 */
@RunWith(JUnitParamsRunner.class)
public class Userinfo extends BaseTest{
    private ASettings aSet = new ASettings();
    private final String M = getString("男" ,male);
    private final String W = getString("女" ,female);
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));
    private String TAG = "jcd_"+this.getClass().getSimpleName();


    private Object[] nickname_change_data(){
        return new Object[]{
                new Object[]{ "明月",true },
                new Object[]{ "明月",false },
        };
    }
    private Object[] head_image_data(){
        return new Object[]{
                new Object[]{true},
                new Object[]{false}
        };
    }

    private Object[] birthday_change_data(){
        return new Object[]{
                new Object[]{"1900-01-01", true},
                new Object[]{"2200-03-04" ,true},
                new Object[]{"1983-03-04" ,false},
                new Object[]{"2004-02-29" ,true},
        };
    }

    @Before
    public void intoSettings(){
        Wish.wantLogin();
        given(ASettings.left_drawer).perform(click());
        given(aSet.drawer_layout).check(matches(isDisplayed()));

    }

    @Test
    @Uncalibrated
    public void default_show_test(){
        String nickname = getText(aSet.nickname);
        given(aSet.personal).perform(click());
        this.compareTakeScreenshot("user info default show");
        given(aSet.nickname_text).check(matches(withText(nickname)));     //默认检查两处昵称一致
    }
    @Test
    @Parameters(method = "nickname_change_data")
    public void nickname_change_test(@Var("newNickname") String newNickname , @Var("confirm") boolean confirm){
        given(aSet.personal).perform(click());
        String nickname = getText(aSet.nickname_text);
        given(aSet.nickname_change_view).perform(click());
        given(aSet.nickname_change_title).check(matches(isDisplayed()));
        if(newNickname.equals(nickname)){
            newNickname = God.getHead2EndString(nickname);     //如果新昵称与原来一致 , 则将昵称收尾置换
            String finalNewNickname = newNickname;
            this.testWatcherAdvance.setUpdateData(new HashMap(){{put(0 , finalNewNickname);}});
        }
        given(aSet.nickname_change_edit)
                .check(matches(withText(nickname)))  //验证会带入原来的昵称
                .perform(clearText())
                .perform(replaceText(newNickname));
        if(confirm) {
            given(aSet.nickname_ok).perform(click());
            given(status_success_toast).check(matches(isDisplayed()));
            given(aSet.nickname_text).check(matches(withText(newNickname)));
            given(Global.back_up).perform(click());
            given(ASettings.left_drawer).perform(click());
            given(aSet.nickname).check(matches(withText(newNickname)));
        }else{
            given(aSet.nickname_cancel).perform(click());
            given(aSet.nickname_text).check(matches(withText(nickname)));
        }
    }

    @Test
    public void gender_change_test() throws Exception {
        given(aSet.personal).perform(click());
        String gender = getText(aSet.gender_text);
        given(aSet.gender_change_view).perform(click());
        if(M.equals(gender)){
            given(aSet.gender_text).check(matches(withText(W)));
            this.back_thrust();
            given(aSet.gender_text).check(matches(withText(W)));
        }else if(W.equals(gender)){
            given(aSet.gender_text).check(matches(withText(M)));
            this.back_thrust();
            given(aSet.gender_text).check(matches(withText(M)));
        }else{
            throw new Exception("gender error : gender = "+gender);
        }
    }

    /**
     * 这是一个回马枪
     */
    private void back_thrust(){


        given(Global.back_up).perform(click());
        given(ASettings.left_drawer).perform(click());
        given(aSet.personal).perform(click());
    }
    @Test
    @Parameters(method = "head_image_data")
    @Uncalibrated
    public void head_image_test(@Var("done_upload") boolean done_upload) {
        Intents.init();
        Uri imageUri = this.head_bitmap_Uri();
        Intent resultData = new Intent();
        resultData.setData(imageUri);
        Log.i(TAG, "setupImageUri: imageUri = "+ imageUri);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        intending(hasAction(Intent.ACTION_GET_CONTENT)).respondWith(result);

        given(aSet.personal).perform(click());
        this.compareTakeScreenshot("before head portrait change");
        given(aSet.head_image_set).perform(click());
        intended(toPackage(AName.DOCUMENTSUI));

        given(aSet.crop_image_view).check(matches(hasDrawable()));
        Intents.release();

        if(done_upload) {
            given(aSet.crop_done_button).perform(click());
            given(status_success_toast).check(matches(isDisplayed()));
            sleep(3000);
            this.compareTakeScreenshot("after head portrait change done");

            //这是一个回马枪
            given(Global.back_up).perform(click());
            given(ASettings.left_drawer).perform(click());
            sleep(2000);
            this.compareTakeScreenshot("after head portrait change done - drawer");
        }else{
            given(aSet.crop_cancel_button).perform(click());
            sleep(3000);
            this.compareTakeScreenshot("after head portrait change cancel");
        }
    }

    @Test
    @Parameters(method = "birthday_change_data")
    public void birthday_change_test(@Var("date") String date , @Var("confirm_change") boolean confirm_change) throws UiObjectNotFoundException {
        Log.d(TAG, "birthday_change_test: date = " + date);
        given(aSet.personal).perform(click());
        String birthday_old = getText(aSet.birthday_text);
        assert birthday_old != null && !"".equals(birthday_old);

        Log.i(TAG, "birthday_change_test: birthday_old = "+birthday_old);
        boolean eq = date.equals(birthday_old);
        Log.d(TAG, "birthday_change_test: eq = " + eq);

        String[] dates = date.split("-");
        given(aSet.birthday_change_view).perform(click());
        birthday_change(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]), dates[2]);

        //if 确认更改生日 , 则更改后的比较会和直接比较字符串的结果相同
        if (confirm_change) {
            given(aSet.birthday_ok).perform(click());
            given(status_success_toast).check(matches(isDisplayed()));
            sleep(2000);    //等待缓冲
            String birthday_new = getText(aSet.birthday_text);
            assert birthday_new != null;

            Log.i(TAG, "birthday_change_test: birthday_new = "+birthday_new);
            assertThat(eq, thisObject(birthday_new.equals(birthday_old)));
        } else {
            // else 取消更改生日 , 则前后的text会是一致的.
            given(aSet.birthday_cancel).perform(click());
            sleep(2000);    //等待缓冲
            String birthday_new = getText(aSet.birthday_text);
            assert birthday_new != null;

            Log.i(TAG, "birthday_change_test: birthday_new = "+birthday_new);
            assertThat(true , thisObject(birthday_new.equals(birthday_old)));
        }
    }

    /**
     * 更改birthday
     * @param year
     * @param month
     * @param day
     * @throws UiObjectNotFoundException
     */
    private void birthday_change(int year ,int month , String day) throws UiObjectNotFoundException {
        given(aSet.birthday_picker_year).perform(click());
        // 设置年份
        onData(hasToString(is(String.valueOf(year))))
                .inAdapterView(((ActivityElement)aSet.birthday_pick_ListView).way())
                .perform(click());

        //设置月份
        int tmp_month = (year-1900)*12 + month-1;   //插件将月份设置成从1900-1 起 token:0 依次递增 , 故有此算法.
        onData(anything())
                .inAdapterView(((ActivityElement)aSet.birthday_pick_ListView).way())
                .atPosition(tmp_month)
                .perform(click());

        //设置日期( espresso 无法定位到元素 , 故使用uiautomator
        uiDevice.findObject(new UiSelector().descriptionStartsWith(day)).click();
    }


    private Uri head_bitmap_Uri(){
        Drawable drawable = getResources().getDrawable(R.drawable.img);
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bmp = bd.getBitmap();
        long t = new Date().getTime();
        Bitmap bitmap = createWatermark(bmp, DoIt.conversionScale(t ,70) , Color.BLUE);


        String name = outPutScreenshot(bitmap , new File(this.matr.getActivity().getCacheDir().getAbsolutePath()+"/"+cacheHeadImage));
        Uri uri = Uri.parse("file://"+name);
        Log.i(TAG, "head_bitmap_Uri: head image Uri = "+ uri);
        return uri;
    }
}
