package com.trubuzz.trubuzz.feature.custom;

import com.trubuzz.trubuzz.shell.Element;

import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;

/**
 * Created by king on 17/6/9.
 */

public class CustomAssert <E>{
    private Element<E> element;

    public CustomAssert(Element<E> element) {
        this.element = element;
    }

    public void assertIsExist(){
        doesNotExist();
    }
}
