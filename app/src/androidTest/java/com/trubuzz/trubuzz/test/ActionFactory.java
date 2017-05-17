package com.trubuzz.trubuzz.test;

import com.trubuzz.trubuzz.utils.MReflect;

/**
 * Created by king on 17/5/17.
 */

public class ActionFactory {

    public <T> T create(Class<T> clz ){
        try {
            return MReflect.getObject(clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
