package com.trubuzz.trubuzz.feature.listen;

import java.util.ArrayList;

/**
 * Created by king on 16/11/14.
 */

public class EventSource {

    private ArrayList<MidwayListener> rep = new ArrayList<MidwayListener>();
    public String testName;
    public String fileName;;
    public Object[] params;

    public void addMidwayListener(MidwayListener midwayListener){
        rep.add(midwayListener);
    }

    public void notifyTestName() {
        for(MidwayListener ml : rep){
            ml.atTestName(this);
        }
    }
    public void notifyTakeScreenshot() {
        for(MidwayListener ml : rep){
            ml.atTakeScreenshot(this);
        }
    }
    public void notifyParameters() {
        for(MidwayListener ml : rep){
            ml.atParameters(this);
        }
    }
}
