package com.trubuzz.trubuzz.feature.custom.assertors;

import com.trubuzz.trubuzz.shell.Element;

import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;

/**
 * Created by king on 17/6/9.
 * 暂时没什么可用价值
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