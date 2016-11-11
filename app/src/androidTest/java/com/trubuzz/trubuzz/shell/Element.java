package com.trubuzz.trubuzz.shell;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.feature.custom.CustomMatcher;
import com.trubuzz.trubuzz.utils.God;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.hasSiblingNoSelf;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withCousin;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withIndex;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withUncle;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by king on 16/11/1.
 */

public class Element <T>{
    private String id;
    private String text;
    private String hint;
    private Element <T>[] children;
    private Element <T>[] sibling;
    private Element <T> cousin;
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

    Element<T> getCousin() {
        return cousin;
    }

    public Element<T> setCousin(Element<T> cousin) {
        this.cousin = cousin;
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

    /**
     * 将封装的element 转换成 matcher list , 摒弃null和空值
     * @param element
     * @return
     */
    private List<Matcher<View>> element2matcher(Element element){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();

        String id = element.getId();
        if(id != null && !id.isEmpty()) ms.add(withResourceName(id));

        String text = element.getText();
        if(text !=null && !text.isEmpty()) ms.add(withText(text));

        String hint = element.getHint();
        if(hint != null && !hint.isEmpty()) ms.add(withHint (hint));

        Element[] children = element.getChildren();
        if(children != null && children.length > 0)  ms.add(children(elements2matcher(children)));

        Element[] sibling = element.getSibling();
        if(sibling != null && sibling.length > 0) ms.add(sibling(elements2matcher(sibling)));

        Element cousin = element.getCousin();
        if(cousin != null) ms.add(withCousin(all(element2matcher(cousin))));

        Element parent = element.getParent();
        if(parent != null ) ms.add(withParent(all(element2matcher(parent))));

        Element uncle = element.getUncle();
        if(uncle != null) ms.add(withUncle(all(element2matcher(uncle))));

        int index = element.getIndex();
        if(index >= 0) ms.add(withIndex(index));

        Class clz = element.getAssignableClass();
        if(clz != null) ms.add(isAssignableFrom(clz));

        Matcher<View>[] matchers = element.getMatchers();
        if(matchers != null && matchers.length > 0) ms.addAll(God.array2list(matchers));

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
            ms.add(element2matcher(element));
        }
        return ms;
    }
    /**
     * 这是通过封装后的匹配方式
     * @param element 封装的元素信息
     * @return
     */
    public ViewInteraction getViewInteraction(Element element){
        Element.ToastMsg toastMsg = element.getToastMsg();
        if(toastMsg != null){
            return getToast(toastMsg.getMsg() , toastMsg.getActivity());
        }
        return onView(all(element2matcher(element)));
    }

    /**
     * 使用自定义的匹配器获取toast
     * @param toastStr
     * @return
     */
    public ViewInteraction getToast_d(String toastStr){
        return onView(withText(toastStr))
                .inRoot(CustomMatcher.withToast());
    }

    /**
     * 使用activity来获取 Context 的方式匹配toast ( 官方使用方法 )
     * 优点 : 清晰的错误日志, 方便调试 ( 推荐使用 )
     * @param toastStr
     * @param activities
     * @return
     */
    public ViewInteraction getToast(String toastStr , ActivityTestRule... activities){
        if(activities.length > 0 && activities[0] != null) {
            return onView(withText(toastStr))
                    .inRoot(withDecorView(not(is(activities[0].getActivity().getWindow().getDecorView()))));
        }
        return onView(withText(toastStr))
                .inRoot(withDecorView(not(is(God.getCurrentActivity(Env.instrumentation).getWindow().getDecorView()))));
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
    private Matcher<View> children(Matcher<View> ...children){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();
        for(Matcher<View> matcher : children){
            ms.add(withChild(matcher));
        }
        return allOf(God.list2array(Matcher.class ,ms));
    }

    /**
     * <List<Matcher<View>> 中包含了单个的sibling的匹配方式
     * @param sibling
     * @return
     */
    private Matcher<View> sibling(List<List<Matcher<View>>> sibling){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();
        for(List<Matcher<View>> matcher : sibling){
            ms.add(hasSiblingNoSelf(all(matcher)));
        }
        return allOf(God.list2array(Matcher.class ,ms));
    }
    private Matcher<View> sibling(Matcher<View> ...sibling){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();
        for(Matcher<View> matcher : sibling){
            ms.add(hasSiblingNoSelf(matcher));
        }
        return allOf(God.list2array(Matcher.class ,ms));
    }

    /**
     * allOf 的封装实现
     * @param matcher
     * @return
     */
    private Matcher<View> all(Matcher<View>... matcher){
        return allOf(matcher);
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
        if(children != null && children.length >0) string += "children=" + Arrays.toString(children) + ", ";
        if(sibling != null && sibling.length >0) string +=  "sibling=" + Arrays.toString(sibling) + ", ";
        if(cousin != null) string += "cousin=" + cousin + ", ";
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
