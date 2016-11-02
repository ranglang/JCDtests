package com.trubuzz.trubuzz.shell;

/**
 * Created by king on 16/10/24.
 */

public class Park {

    /**
     * @deprecated use {@link #given(Element)} instead .
     * 1. 避免泛型不安全性
     * 2. 已经使用了Element做封装, 可满足各式各样的需求
     * @param viewInteractionDesc
     * @param <T>
     * @return
     */
    public static <T> AdViewInteraction given(T viewInteractionDesc){
        return new AdViewInteraction(WithAny.getViewInteraction(viewInteractionDesc));
    }

    public static AdViewInteraction given(Element element){
        return new AdViewInteraction(WithAny.getViewInteraction(element));
    }
}
