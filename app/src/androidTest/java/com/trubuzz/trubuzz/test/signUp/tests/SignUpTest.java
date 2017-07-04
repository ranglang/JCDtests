package com.trubuzz.trubuzz.test.signUp.tests;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.signUp.SignUpAction;
import com.trubuzz.trubuzz.test.signUp.SignUpService;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Rule;

/**
 * Created by king on 2017/7/3.
 */

public class SignUpTest extends BaseTest {
    private SignUpService ss = new SignUpAction();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));



}
