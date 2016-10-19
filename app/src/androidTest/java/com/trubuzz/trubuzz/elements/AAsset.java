package com.trubuzz.trubuzz.elements;

import android.support.test.espresso.ViewInteraction;

import static com.trubuzz.trubuzz.elements.WithAny.getViewInteraction;
import static com.trubuzz.trubuzz.test.R.string.net_liqidation;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2016/10/19.
 */

public class AAsset {
    private static final String [] ID_TEXT_net_worth = {"title",getString("净资产", net_liqidation)};


    public static ViewInteraction net_worth(){
        return getViewInteraction(ID_TEXT_net_worth);
    }
}
