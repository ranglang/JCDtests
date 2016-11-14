package com.trubuzz.trubuzz.feature.listen;

import java.util.ArrayList;

/**
 * Created by king on 16/11/14.
 */

public class EventSource {

    private ArrayList<MidwayListener> rep = new ArrayList<MidwayListener>();

    public void addMidwayListener(MidwayListener midwayListener){
        rep.add(midwayListener);
    }

    public void notifyDemoEvent() {

    }
}
