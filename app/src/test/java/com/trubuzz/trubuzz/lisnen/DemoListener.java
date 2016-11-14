package com.trubuzz.trubuzz.lisnen;

import java.util.EventListener;

/**
 * Created by king on 16/11/14.
 */

public interface DemoListener extends EventListener{

    public void handleEvent(DemoEvent dm);
}
