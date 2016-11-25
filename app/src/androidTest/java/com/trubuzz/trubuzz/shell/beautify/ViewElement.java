package com.trubuzz.trubuzz.shell.beautify;

import android.app.Activity;
import android.view.View;

import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.utils.Find;
import com.trubuzz.trubuzz.utils.God;

import org.hamcrest.Matcher;

import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withView;

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
    public Matcher interactionWay() {
//        View view = activity.findViewById(Find.byShortId(viewId));
//        return allOf(withView(getElementView(this)), isDisplayed());
        return withView(getElementView(this));
    }

    private View getElementView(Element element){
        ViewElement ve = (ViewElement) element;
        if(ve.viewId != null){
            return activity.findViewById(Find.byShortId(viewId));
        }
        if(ve.child != null){
            return withChild(getElementView(ve.child));
        }
        return null;
    }

    private View withChild(View view){
        return (View) view.getParent();
    }
}
