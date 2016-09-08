package com.trubuzz.trubuzz.idlingResource;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.trubuzz.trubuzz.myInterface.ActivityLifecycleIdlingResource;

import static android.support.test.espresso.intent.Checks.checkNotNull;

/**
 * Created by king on 2016/9/8.
 */
public class WebViewIdlingResource extends WebChromeClient implements ActivityLifecycleIdlingResource<WebView> {

    private static final int FINISHED = 100;

    private WebView webView;
    private ResourceCallback callback;


    @Override
    public void inject(WebView webView) {
        this.webView = checkNotNull(webView,String.format("Trying to instantiate a \'%s\' with a null WebView", getName()));
        // Save the original client if needed.
        this.webView.setWebChromeClient(this);
    }

    @Override public void clear() {
        // Free up the reference to the webView
        webView = null;
    }



    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress == FINISHED && view.getTitle() != null && callback != null) {
            callback.onTransitionToIdle();
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (webView.getProgress() == FINISHED && callback != null) {
            callback.onTransitionToIdle();
        }
    }

    @Override
    public String getName() {
        return "WebView idling resource";
    }

    @Override public boolean isIdleNow() {
        // The webView hasn't been injected yet, so we're idling
        if (webView == null) return true;
        return webView.getProgress() == FINISHED && webView.getTitle() != null;
    }

    @Override public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.callback = resourceCallback;
    }
}