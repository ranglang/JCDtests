package com.trubuzz.trubuzz.shell.beautify;

import android.view.View;

import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.utils.God;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.hasSiblingNoSelf;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withCousin;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withIndex;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withUncle;
import static com.trubuzz.trubuzz.utils.DoIt.notEmpty;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by king on 16/11/1.
 */

public class ActivityElement implements Element<Matcher<View>>{
    private String id;
    private String text;
    private String hint;
    private Element<Matcher<View>>[] children;
    private Element<Matcher<View>>[] sibling;
    private Element<Matcher<View>>[] cousinry;
    private Element<Matcher<View>> parent;
    private Element<Matcher<View>> uncle;
    private Element<Matcher<View>> descendant;
    private Element<Matcher<View>> ancestor;
    private int index = -1;
    private Class<? extends View> assignableClass;
    private Matcher[] matchers;
    private boolean dis = true;
    private String content_desc;

    String getId() {
        return id;
    }

    String getText() {
        return text;
    }

    String getHint() {
        return hint;
    }

    Element<Matcher<View>>[] getChildren() {
        return children;
    }

    Element<Matcher<View>>[] getSibling() {
        return sibling;
    }

    Element<Matcher<View>>[] getCousinry() {
        return cousinry;
    }

    Element<Matcher<View>> getParent() {
        return parent;
    }

    Element<Matcher<View>> getUncle() {
        return uncle;
    }

    int getIndex() {
        return index;
    }

    Class<? extends View> getAssignableClass() {
        return assignableClass;
    }

    Matcher[] getMatchers() {
        return matchers;
    }

    boolean isDis() {
        return dis;
    }

    Element<Matcher<View>> getDescendant() {
        return descendant;
    }

    Element<Matcher<View>> getAncestor() {
        return ancestor;
    }

    String getContent_desc() {
        return content_desc;
    }

    public ActivityElement setDescendant(Element<Matcher<View>> descendant) {
        this.descendant = descendant;
        return this;
    }

    public ActivityElement setAncestor(Element<Matcher<View>> ancestor) {
        this.ancestor = ancestor;
        return this;
    }

    public ActivityElement setId(String id) {
        this.id = id;
        return this;
    }

    public ActivityElement setText(String text) {
        this.text = text;
        return this;
    }

    public ActivityElement setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public ActivityElement setChildren(Element<Matcher<View>>... children) {
        this.children = children;
        return this;
    }

    public ActivityElement setSibling(Element<Matcher<View>>... sibling) {
        this.sibling = sibling;
        return this;
    }

    public ActivityElement setCousinry(Element<Matcher<View>>... cousinry) {
        this.cousinry = cousinry;
        return this;
    }

    public ActivityElement setParent(Element<Matcher<View>> parent) {
        this.parent = parent;
        return this;
    }

    public ActivityElement setUncle(Element<Matcher<View>> uncle) {
        this.uncle = uncle;
        return this;
    }

    public ActivityElement setIndex(int index) {
        this.index = index;
        return this;
    }

    public ActivityElement setAssignableClass(Class<? extends View> assignableClass) {
        this.assignableClass = assignableClass;
        return this;
    }

    @SafeVarargs
    final public <T> ActivityElement  setMatchers(Matcher<? super T>... matchers) {
        this.matchers = matchers;
        return this;
    }

    public ActivityElement setDis(boolean dis) {
        this.dis = dis;
        return this;
    }

    public ActivityElement setContent_desc(String content_desc) {
        this.content_desc = content_desc;
        return this;
    }

    @Override
    public Matcher<View> way() {
        return all(new ElementHandle().element2matcher(this));
    }

    private Matcher<View> all(List<Matcher<View>> list){
        return allOf(God.list2array(Matcher.class ,list));
    }

    @Override
    public String toString() {
        String string = "{";
        if(id != null) string += "id='" + id + "', ";
        if(text != null) string += "text='" + text + "', ";
        if(hint != null) string += "hint='" + hint + "', ";
        if(content_desc != null) string += "content_desc='" + content_desc + "', ";
        if(children != null && children.length >0) string += "children=" + Arrays.toString(children) + ", ";
        if(sibling != null && sibling.length >0) string +=  "sibling=" + Arrays.toString(sibling) + ", ";
        if(cousinry != null&& cousinry.length >0) string += "cousinry=" + Arrays.toString(cousinry) + ", ";
        if(parent != null) string += "parent=" + parent + ", ";
        if(uncle != null) string += "uncle=" + uncle + ", ";
        if(ancestor != null) string += "ancestor=" + ancestor + ", ";
        if(descendant != null) string += "descendant=" + descendant + ", ";
        if(index != -1) string += "index=" + index + ", ";
        if(assignableClass != null) string += "assignableClass=" + assignableClass + ", ";
//        if(toastMsg != null) string += "toastMsg='" + toastMsg.getMsg() + "', ";
        if(matchers != null) string += "matchers=" + Arrays.toString(matchers) + ", " ;

        if(string.endsWith(", ")) string = string.substring(0,string.length()-2);
        return string += '}';
    }



}
