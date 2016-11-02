package com.trubuzz.trubuzz.shell;

import org.hamcrest.Matcher;

/**
 * Created by king on 16/11/1.
 */

public class Element <T>{
    private String id;
    private String text;
    private String hint;
    private Element <T>[] children;
    private Element <T>[] sibling;
    private Element <T> parent;
    private Element <T> uncle;
    private int index = -1;
    private Class assignableClass;
    private Matcher<? super T>[] matchers;

    String getId() {
        return id;
    }

    public Element <T> setId(String id) {
        this.id = id;
        return this;
    }

    String getText() {
        return text;
    }

    public Element <T> setText(String text) {
        this.text = text;
        return this;
    }

    String getHint() {
        return hint;
    }

    public Element <T> setHint(String hint) {
        this.hint = hint;
        return this;
    }

    Element[] getChildren() {
        return children;
    }

    public Element <T> setChildren(Element... children) {
        this.children = children;
        return this;
    }

    Element[] getSibling() {
        return sibling;
    }

    public Element <T> setSibling(Element... sibling) {
        this.sibling = sibling;
        return this;
    }

    Element getParent() {
        return parent;
    }

    public Element <T> setParent(Element parent) {
        this.parent = parent;
        return this;
    }

    Element getUncle() {
        return uncle;
    }

    public Element <T> setUncle(Element uncle) {
        this.uncle = uncle;
        return this;
    }

    int getIndex() {
        return index;
    }

    public Element <T> setIndex(int index) {
        this.index = index;
        return this;
    }

    Class getAssignableClass() {
        return assignableClass;
    }

    public Element <T> setAssignableClass(Class assignableClass) {
        this.assignableClass = assignableClass;
        return this;
    }

    Matcher<? super T>[] getMatchers() {
        return matchers;
    }

    @SafeVarargs
    final public Element <T> setMatchers(Matcher<? super T>... matchers) {
        this.matchers = matchers;
        return this;
    }
}
