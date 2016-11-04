package com.trubuzz.trubuzz.shell;

import android.support.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;

import java.util.Arrays;

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
    private ToastMsg toastMsg;
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

    ToastMsg getToastMsg() {
        return toastMsg;
    }

    public Element<T> setToastMsg(String msg, ActivityTestRule activity) {
        this.toastMsg = new ToastMsg(msg , activity);
        return this;
    }
    public Element<T> setToastMsg(String msg) {
        this.toastMsg = new ToastMsg(msg );
        return this;
    }

    @Override
    public String toString() {
        String string = "{";
        if(id != null) string += "id='" + id + "', ";
        if(text != null) string += "text='" + text + "', ";
        if(hint != null) string += "hint='" + hint + "', ";
        if(children != null && children.length >0) string += "children=" + Arrays.toString(children) + ", ";
        if(sibling != null && sibling.length >0) string +=  "sibling=" + Arrays.toString(sibling) + ", ";
        if(parent != null) string += "parent=" + parent + ", ";
        if(uncle != null) string += "uncle=" + uncle + ", ";
        if(index != -1) string += "index=" + index + ", ";
        if(assignableClass != null) string += "assignableClass=" + assignableClass + ", ";
        if(toastMsg != null) string += "toastMsg='" + toastMsg.getMsg() + "', ";
        if(matchers != null) string += "matchers=" + Arrays.toString(matchers) + ", " ;

        if(string.endsWith(", ")) string = string.substring(0,string.length()-2);
        return string += '}';
    }

    /*******************/
    class ToastMsg{
        private String msg;
        private ActivityTestRule activity;

        ToastMsg(String msg, ActivityTestRule activity) {
            this.msg = msg;
            this.activity = activity;
        }
        ToastMsg(String msg) {
            this.msg = msg;
        }

        String getMsg() {
            return msg;
        }

        ActivityTestRule getActivity() {
            return activity;
        }
    }
}
