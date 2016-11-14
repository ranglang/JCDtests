package com.trubuzz.trubuzz.shell;

import android.support.test.espresso.web.assertion.WebAssertion;
import android.support.test.espresso.web.model.Atom;
import android.support.test.espresso.web.model.ElementReference;
import android.support.test.espresso.web.sugar.Web;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.web.sugar.Web.onWebView;

/**
 * Created by king on 16/11/9.
 */

public class AdWebInteraction<R> {
    private Web.WebInteraction<R> webInteraction;

    private AdWebInteraction(Web.WebInteraction<R> webInteraction){
        this.webInteraction = webInteraction;
    }

    /**
     * 封装onWebView
     * @return
     */
    public static AdWebInteraction webGiven(){
        return new AdWebInteraction(onWebView());
    }
    public static AdWebInteraction webGiven(Matcher<View> viewMatcher) {
        return new AdWebInteraction(onWebView(viewMatcher));
    }

    /**
     * 封装check
     * @param assertion
     * @param <E>
     * @return
     */
    public <E> AdWebInteraction<E> check(WebAssertion<E> assertion){
        return  new AdWebInteraction<E>(webInteraction.check(assertion));
    }
    /**
     * 封装get
     * @return
     */
    public R get() {
        return webInteraction.get();
    }

    /**
     * 封装perform
     * @param atom
     * @param <E>
     * @return
     */
    public <E> AdWebInteraction<E> perform(Atom<E> atom) {
        return new AdWebInteraction<E>(webInteraction.perform(atom));
    }
    public <E> AdWebInteraction<E> perform(Element<Atom> atom) {
        return perform(atom.interactionWay());
    }
    /**
     * 封装reset
     * @return
     */
    public AdWebInteraction<R> reset() {
        return new AdWebInteraction<R>(webInteraction.reset());
    }
    /**
     * 封装forceJavascriptEnabled
     * @return
     */
    public AdWebInteraction<R> forceJavascriptEnabled() {
        webInteraction.forceJavascriptEnabled();
        return this;
    }

    /**
     * 封装withElement
     * @param elementPicker
     * @return
     */
    public AdWebInteraction<R> withElement(Atom<ElementReference> elementPicker) {
        return new AdWebInteraction<R>(webInteraction.withElement(elementPicker));
    }
    public AdWebInteraction<R> withElement(Element<Atom<ElementReference>> elementPicker) {
        return withElement(elementPicker.interactionWay());
    }
    /**
     * 封装withContextualElement
     * @param elementPicker
     * @return
     */
    public AdWebInteraction<R> withContextualElement(Atom<ElementReference> elementPicker) {
        return new AdWebInteraction<R>(webInteraction.withContextualElement(elementPicker));
    }
    public AdWebInteraction<R> withContextualElement(Element<Atom<ElementReference>> elementPicker) {
        return withContextualElement(elementPicker.interactionWay());
    }
    /**
     * 封装withTimeout
     * @param amount
     * @param unit
     * @return
     */
    public AdWebInteraction<R> withTimeout(long amount, TimeUnit unit) {
        return new AdWebInteraction<R>(webInteraction.withTimeout(amount,unit));
    }

    /**
     * 封装withNoTimeout
     * @return
     */
    public AdWebInteraction<R> withNoTimeout(){
        return new AdWebInteraction<R>(webInteraction.withNoTimeout());
    }
}
