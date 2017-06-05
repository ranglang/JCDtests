package com.trubuzz.trubuzz.test;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Rule;

/**
 * Created by king on 2016/10/19.
 */

public class AssetTest  {

    private static final String TAG = "jcd_AssetTest ";
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

   // @Before
    public void into_asset(){
        Wish.wantBrokerLogin();
    }


}
