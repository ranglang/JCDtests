package com.trubuzz.trubuzz.shell.beautify;

import android.app.Activity;
import android.view.View;

import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.utils.Find;
import com.trubuzz.trubuzz.utils.God;

import org.hamcrest.Matcher;

import static com.trubuzz.trubuzz.feature.custom.matchers.CustomMatcher.withView;
import static com.trubuzz.trubuzz.utils.DoIt.notEmpty;

/**
 * Created by king on 16/11/22.
 */

public class ViewElement implements Element<Matcher> {
    private String viewId;
    private Activity activity;
    private Element child;

    public ViewElement(Activity activity) {
        this.activity = activity;
    }
    public ViewElement(){
        this.activity = God.getCurrentActivity(Env.instrumentation);
    }
    public ViewElement setViewId(String viewId) {
        this.viewId = viewId;
        return this;
    }

    public ViewElement setChild(Element child) {
        this.child = child;
        return this;
    }

    @Override
    public Matcher way() {
//        View view = activity.findViewById(Find.byShortId(viewId));
//        return allOf(withView(getElementView(this)), isDisplayed());
        return withView(getElementView(this));
    }

    /**
     * 由于findViewById 只会匹配第一个匹配到的View
     * @param element
     * @return
     */
    private View getElementView(Element element){
        ViewElement ve = (ViewElement) element;
        if(notEmpty(ve.viewId))
            return ve.activity.findViewById(Find.byShortId(ve.viewId));

        if(notEmpty(ve.child))  return withChild(getElementView(ve.child));

        return null;
    }

    private View withChild(View view){
        return (View) view.getParent();
    }
}
