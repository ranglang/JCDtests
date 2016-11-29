package com.trubuzz.trubuzz.elements;

import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;

import static com.trubuzz.trubuzz.test.R.string.portfolio_set;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 16/11/22.
 */

public class AWealth {
    private static final Element subscribe_button = new ActivityElement().setId("subscribe");


    public static final Element portfolio_button = new ActivityElement().setId("button1")
            .setText(getString("Portfolio" ,portfolio_set));    //投资组合按钮



    public static final Element default_portfolio = new ActivityElement().setIndex(1).
            setChildren(
                new ActivityElement().setId("avatar") ,
                new ActivityElement().setChildren(subscribe_button)
            ); //一项投资组合
}
