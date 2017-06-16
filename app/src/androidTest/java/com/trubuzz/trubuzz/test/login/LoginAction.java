package com.trubuzz.trubuzz.test.login;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.AAsset;
import com.trubuzz.trubuzz.idlingResource.ActivityIdlingResource;
import com.trubuzz.trubuzz.idlingResource.ViewIdlingResource;
import com.trubuzz.trubuzz.shell.AdWebInteraction;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static com.trubuzz.trubuzz.constant.Env.instrumentation;
import static com.trubuzz.trubuzz.elements.Global.assets_radio;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.isPassword;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getView;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.shell.Park.webGiven;
import static com.trubuzz.trubuzz.test.R.string.input_password;
import static com.trubuzz.trubuzz.utils.DoIt.unRegIdlingResource;
import static com.trubuzz.trubuzz.utils.God.getString;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by king on 17/5/25.
 */

public class LoginAction implements LoginService{
    private LoginView lv = new LoginView();

    @Override
    public void browse_tutorial(){
        given(lv.tutorial_1_title).check(matches(withText(lv.tutorial_1_title_text)));
        given(lv.tutorial_1_content).check(matches(withText(lv.tutorial_1_content_text)));
        given(isRoot()).perform(swipeLeft());

        given(lv.tutorial_1_title).check(matches(withText(lv.tutorial_2_title_text)));
        given(lv.tutorial_1_content).check(matches(withText(lv.tutorial_2_content_text)));
        given(isRoot()).perform(swipeLeft());

        given(lv.tutorial_1_title).check(matches(withText(lv.tutorial_3_title_text)));
        given(lv.tutorial_1_content).check(matches(withText(lv.tutorial_3_content_text)));
        given(isRoot()).perform(swipeLeft());

        given(lv.tutorial_1_title).check(matches(withText(String.format(lv.tutorial_4_title_text,
                God.getAppName(God.getCurrentActivity(instrumentation))))));
        given(lv.tutorial_start_button).check(matches(isDisplayed()))
                .perform(click());
    }

    @Override
    public void type_username(String username) {
        given(lv.username_input).perform(clearText())
                .perform(typeText(username))
                .check(matches(withText(username)));
    }

    @Override
    public void type_password(String pwd) {
        given(lv.pwd_input).perform(clearText())
                .perform(typeText(pwd))
                .check(matches(isPassword()));
    }

    @Override
    public void click_login_button() {
        closeSoftKeyboard();
        given(lv.login_button).perform(click());
    }

    @Override
    public void check_broker() {
        given(assets_radio).perform(click());
        given(AAsset.net_worth_view).check( matches((isDisplayed())));
    }

    @Override
    public void check_not_broker() {
        DoIt.regIdlingResource(new ActivityIdlingResource(AName.MAIN, instrumentation.getContext(), true));
       AdWebInteraction a =  webGiven()
                .withElement(lv.ib_broker_title);
               a .check(webMatches(getText(), equalTo(lv.ib_broker_title_text)));
        unRegIdlingResource();
    }

    @Override
    public void clean_password() {
        given(lv.clean_pwd_image).perform(click());
    }

    @Override
    public void check_password_input_default_show() {
        given(lv.pwd_input).check(matches(withHint(lv.pwd_input_hint)));
    }
}
