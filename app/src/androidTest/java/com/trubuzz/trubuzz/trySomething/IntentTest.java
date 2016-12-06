package com.trubuzz.trubuzz.trySomething;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.ASettings;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.R;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static com.trubuzz.trubuzz.constant.Env.testPackageName;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.utils.DoIt.createWatermark;
import static com.trubuzz.trubuzz.utils.DoIt.outPutScreenshot;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;
import static com.trubuzz.trubuzz.utils.God.getResources;

/**
 * Created by king on 16/11/30.
 */
public class IntentTest extends BaseTest{
    private Instrumentation.ActivityResult result;
    private Matcher<Intent> expectedIntent;
    private AssetManager asset ;

    @Rule
//    public IntentsTestRule<?> mIntentsRule = new IntentsTestRule(God.getFixedClass(AName.MAIN));
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));
    private String TAG = "jcd_" + this.getClass().getSimpleName();

    @Before
    public void stubAllExternalIntents() throws IOException {
//        Context context = matr.getActivity();
//        InputStream in = context.getClass().getClassLoader().getResourceAsStream("assets/ic_launcher.png");

        // By default Espresso Intents does not stub any Intents.
        // Stubbing needs to be setup before every test run. In this case all external Intents will be blocked.
        Intents.init();
//        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));

        Resources resources = matr.getActivity().getResources();

        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + testPackageName + '/'
                + R.drawable.class.getSimpleName() + '/'
                + "img" + 1);
//                + "mipmap" + '/'
//                + "ic_launcher");

//        imageUri = Uri.parse("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
//        imageUri = Uri.parse("file:///data/data/com.trubuzz.trubuzz/files/jcd.png");  ok
//        imageUri = Uri.parse("file:///data/tmp/fp.jpg");

        imageUri = this.elementTest();
//        File file = new File(Env.filesDir + "aa/123.png");
//        DoIt.takeScreenshot(Env.uiDevice , file);
//        imageUri = Uri.parse("file://" + file.getAbsolutePath());

        Log.i("t123 ", "setupImageUri: imageUri = "+ imageUri);
        Intent resultData = new Intent();
        resultData.setData(imageUri);

        int a = 0x7f030000;

        result = new Instrumentation.ActivityResult(Activity.RESULT_OK , resultData);

//        expectedIntent = allOf(hasAction(Intent.ACTION_GET_CONTENT),hasData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
        expectedIntent = hasAction(Intent.ACTION_GET_CONTENT);

        intending(expectedIntent).respondWith(result);
    }

    @Test
    public void tt(){
        ASettings aSettings = new ASettings();
        Wish.wantLogin();
        given(ASettings.left_drawer).perform(click());
        given(aSettings.personal).perform(click());
        onView(withResourceName("avatar")).perform(click());
        intended(expectedIntent);
        sleep(2000);
        given(aSettings.crop_done_button).perform(click());

    }

    public Uri elementTest(){
//        Ut u = new Ut();
        Drawable drawable = getResources().getDrawable(R.drawable.img);
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bmp = bd.getBitmap();
        long t = new Date().getTime();
        Bitmap bitmap = createWatermark(bmp, DoIt.conversionScale(t ,70) ,t);

//        String name = outPutScreenshot(this.matr.getActivity(), bitmap, "hello haha");
        String name = outPutScreenshot(bitmap , new File(this.matr.getActivity().getCacheDir().getAbsolutePath()+"/img.png"));
        Log.i(TAG, "elementTest: name = "+name);

        Uri uri = Uri.parse("file://"+name);
        return uri;
    }

}
