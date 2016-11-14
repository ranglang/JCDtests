package com.trubuzz.trubuzz.utils;

import java.util.HashMap;

/**
 * Created by king on 16/11/14.
 */

public class SHashMap<K, V> extends HashMap<K, V> {

    @Override
    public V put(K key, V value) {
        return super.put(key , (V) value.toString());
    }
}
