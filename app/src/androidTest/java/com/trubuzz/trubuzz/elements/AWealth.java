package com.trubuzz.trubuzz.elements;

import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;
import com.trubuzz.trubuzz.shell.beautify.ViewElement;

import static com.trubuzz.trubuzz.test.R.string.portfolio_set;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 16/11/22.
 */

public class AWealth {
    public static final Element portfolio_button = new ActivityElement().setId("button1")
            .setText(getString("Portfolio" ,portfolio_set));    //投资组合按钮


    public static final Element default_portfolio = new ViewElement().setChild(
            new ViewElement().setViewId("title"));          //一项投资组合
}
