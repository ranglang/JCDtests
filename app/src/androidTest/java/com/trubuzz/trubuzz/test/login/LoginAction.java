package com.trubuzz.trubuzz.test.login;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.shell.Park.given;

/**
 * Created by king on 17/5/25.
 */

public class LoginAction {
    LoginView lv = new LoginView();

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

        given(lv.tutorial_1_title).check(matches(withText(lv.tutorial_4_title_text)));
        given(lv.tutorial_start_button).check(matches(isDisplayed()))
                .perform(click());
    }
}
