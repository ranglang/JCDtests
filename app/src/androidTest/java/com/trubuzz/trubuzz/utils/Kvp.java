package com.trubuzz.trubuzz.utils;

/**
 * Created by king on 2017/6/21.
 * 键值对的模板
 */

public class Kvp<K,V> {
    private K key;
    private V value;

    public Kvp() {
    }

    public Kvp(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
