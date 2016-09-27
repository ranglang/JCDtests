package com.trubuzz.trubuzz.elements;

import android.support.test.espresso.web.model.Atom;
import android.support.test.espresso.web.model.ElementReference;
import android.support.test.espresso.web.webdriver.Locator;

import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;

/**
 * Created by king on 2016/9/7.
 * 代理选择页面 , 这里是 WebView 实现
 *
 */
public class EBrokerChoose {
    private static Atom<ElementReference> v_IBBroker;


    private static final String CSS_IB = "span.broker_logo>span.ng-binding";


    public static Atom<ElementReference> ibBrokerTitle(){
        return v_IBBroker = v_IBBroker == null
                ? findElement(Locator.CSS_SELECTOR,CSS_IB) : v_IBBroker;
    }
}
