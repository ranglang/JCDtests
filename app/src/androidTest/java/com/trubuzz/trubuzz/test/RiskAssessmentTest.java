package com.trubuzz.trubuzz.test;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Rule;

/**
 * Created by king on 16/12/21.
 */

public class RiskAssessmentTest {
    private String TAG = "jcd_"+this.getClass().getSimpleName();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));


}
