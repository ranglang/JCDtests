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
    private int index = -1;
    private Class assignableClass;
    private Matcher[] matchers;
    private boolean dis = true;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getHint() {
        return hint;
    }

    public Element<Matcher<View>>[] getChildren() {
        return children;
    }

    public Element<Matcher<View>>[] getSibling() {
        return sibling;
    }

    public Element<Matcher<View>>[] getCousinry() {
        return cousinry;
    }

    public Element<Matcher<View>> getParent() {
        return parent;
    }

    public Element<Matcher<View>> getUncle() {
        return uncle;
    }

    public int getIndex() {
        return index;
    }

    public Class getAssignableClass() {
        return assignableClass;
    }

    public Matcher[] getMatchers() {
        return matchers;
    }

    public boolean isDis() {
        return dis;
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

    public final ActivityElement setCousinry(Element<Matcher<View>>... cousinry) {
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

    public ActivityElement setAssignableClass(Class assignableClass) {
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

    @Override
    public Matcher<View> interactionWay() {
//        return all(this.element2matcher());
        return all(new ElementHandle().element2matcher(this));
    }

    @Override
    public String toString() {
        String string = "{";
        if(id != null) string += "id='" + id + "', ";
        if(text != null) string += "text='" + text + "', ";
        if(hint != null) string += "hint='" + hint + "', ";
        if(children != null && children.length >0) string += "children=" + Arrays.toString(children) + ", ";
        if(sibling != null && sibling.length >0) string +=  "sibling=" + Arrays.toString(sibling) + ", ";
        if(cousinry != null&& cousinry.length >0) string += "cousinry=" + Arrays.toString(cousinry) + ", ";
        if(parent != null) string += "parent=" + parent + ", ";
        if(uncle != null) string += "uncle=" + uncle + ", ";
        if(index != -1) string += "index=" + index + ", ";
        if(assignableClass != null) string += "assignableClass=" + assignableClass + ", ";
//        if(toastMsg != null) string += "toastMsg='" + toastMsg.getMsg() + "', ";
        if(matchers != null) string += "matchers=" + Arrays.toString(matchers) + ", " ;

        if(string.endsWith(", ")) string = string.substring(0,string.length()-2);
        return string += '}';
    }

/************************** 代码已重构 , 以下部分将废弃 **************************/
    /**
     * 将封装的element 转换成 matcher list , 摒弃null和空值
     * @return
     */
    private List<Matcher<View>> element2matcher(){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();

        if(notEmpty(id)) ms.add(withResourceName(id));

        if(notEmpty(text)) ms.add(withText(text));

        if(notEmpty(hint)) ms.add(withHint (hint));

        if(notEmpty(children))  ms.add(children(elements2matcher(children)));
//        if(notEmpty(children)) ms.add(children(elements2matcher2(children)));

        if(notEmpty(sibling)) ms.add(sibling(elements2matcher(sibling)));

        if(notEmpty(cousinry)) ms.add(cousin(elements2matcher(cousinry)));

        if(notEmpty(parent)) ms.add(withParent(all(((ActivityElement)parent).element2matcher())));

        if(notEmpty(uncle)) ms.add(withUncle(all(((ActivityElement)uncle).element2matcher())));

        if(index >= 0) ms.add(withIndex(index));

        if(notEmpty(assignableClass)) ms.add(isAssignableFrom(assignableClass));

        if(notEmpty(matchers)) ms.addAll(God.<Matcher<View>>array2list(matchers));

        if(dis)   ms.add(isDisplayed());

        return ms;
    }

    /**
     * 多element 的合并
     * @param elements
     * @return
     */
    private List<List<Matcher<View>>> elements2matcher(Element... elements){
        List<List<Matcher<View>>> ms = new ArrayList<>();
        for(Element element : elements){
            ms.add(((ActivityElement)element).element2matcher());
        }
        return ms;
    }
    ///---
    @SafeVarargs
    private final List<Matcher<View>> elements2matcher2(Element<Matcher<View>>... elements){
        List<Matcher<View>> ms = new ArrayList<>();
        for(Element<Matcher<View>> element : elements){
            ms.add(element.interactionWay());
        }
        return ms;
    }

    /**
     * List<Matcher<View>>中包含了单个child的匹配方式,
     * 使用all(List<Matcher<View>> list ) 提取出child的Matcher<View> .
     * 如果有多个children 则使用allOf 多次匹配即可
     * @param children
     * @return
     */
    private Matcher<View> children(List<List<Matcher<View>>> children){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();
        for(List<Matcher<View>> matcher : children){
            ms.add(withChild(all(matcher)));
        }
        return allOf(God.list2array(Matcher.class ,ms));
    }
    @SafeVarargs
    private final Matcher<View> children(Matcher<View>... children){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();
        for(Matcher<View> matcher : children){
            ms.add(withChild(matcher));
        }
        return allOf(God.list2array(Matcher.class ,ms));
    }

    /**
     * <List<Matcher<View>> 中包含了单个的sibling的匹配方式
     * @param siblings
     * @return
     */
    private Matcher<View> sibling(List<List<Matcher<View>>> siblings){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();
        for(List<Matcher<View>> matcher : siblings){
            ms.add(hasSiblingNoSelf(all(matcher)));
        }
        return allOf(God.list2array(Matcher.class ,ms));
    }
    @SafeVarargs
    private final Matcher<View> sibling(Matcher<View>... siblings){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();
        for(Matcher<View> matcher : siblings){
            ms.add(hasSiblingNoSelf(matcher));
        }
        return allOf(God.list2array(Matcher.class ,ms));
    }

    /**
     * <List<Matcher<View>> 中包含了单个的cousin的匹配方式
     * @param cousins
     * @return
     */
    private Matcher<View> cousin(List<List<Matcher<View>>> cousins ){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();
        for(List<Matcher<View>> matcher : cousins ){
            ms.add(withCousin(all(matcher)));
        }
        return allOf(God.list2array(Matcher.class ,ms));
    }
    /**
     * allOf 的封装实现
     * @param matcher
     * @return
     */
    @SafeVarargs
    private final Matcher<View> all(Matcher<View>... matcher){
        return allOf(matcher);
    }

    private Matcher<View> all(List<Matcher<View>> list){
        return allOf(God.list2array(Matcher.class ,list));
    }

}
