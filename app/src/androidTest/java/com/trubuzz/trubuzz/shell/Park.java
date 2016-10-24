package com.trubuzz.trubuzz.shell;

import com.trubuzz.trubuzz.elements.WithAny;

/**
 * Created by king on 16/10/24.
 */

public class Park {

    public static <T> AdViewInteraction give(T viewInteractionDesc){
        return new AdViewInteraction(WithAny.getViewInteraction(viewInteractionDesc));
    }

}
