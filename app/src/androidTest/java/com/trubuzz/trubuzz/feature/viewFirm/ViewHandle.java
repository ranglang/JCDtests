package com.trubuzz.trubuzz.feature.viewFirm;

import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.UiController;
import android.support.test.espresso.base.MainThread;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

import static android.support.test.internal.util.Checks.checkNotNull;

/**
 * Created by king on 16/11/24.
 */

public class ViewHandle {
    private static final String TAG = "jcd_"+ViewHandle.class.getSimpleName();

    private List<View> targetViews;
    private ViewTracer viewTracer;
    private final UiController uiController;
    private volatile FailureHandler failureHandler;
    private final Matcher<View> viewMatcher;
    private final Executor mainThreadExecutor;

    public ViewHandle(
            UiController uiController,
            @MainThread Executor mainThreadExecutor,
            FailureHandler failureHandler,
            Matcher<View> viewMatcher,
            ViewTracer viewTracer
    ){
        this.uiController = checkNotNull(uiController);
        this.failureHandler = checkNotNull(failureHandler);
        this.mainThreadExecutor = checkNotNull(mainThreadExecutor);
        this.viewMatcher = checkNotNull(viewMatcher);
        this.viewTracer = viewTracer;
    }

    /**
     * 获得匹配的多个View
     * @return
     */
    public List<View> getTargetViews() {
        runSynchronouslyOnUiThread(new Runnable() {
            @Override
            public void run() {
                uiController.loopMainThreadUntilIdle();
                targetViews = viewTracer.getViews();
            }
        });
        return targetViews;
    }


    /**
     * same {@link android.support.test.espresso.ViewInteraction {@link #runSynchronouslyOnUiThread(Runnable)}}
     * @param action
     */
    private void runSynchronouslyOnUiThread(Runnable action) {
        FutureTask<Void> uiTask = new FutureTask<Void>(action, null);
        mainThreadExecutor.execute(uiTask);
        try {
            uiTask.get();
        } catch (InterruptedException ie) {
            throw new RuntimeException("Interrupted running UI task", ie);
        } catch (ExecutionException ee) {
            failureHandler.handle(ee.getCause(), viewMatcher);
        }
    }
}
