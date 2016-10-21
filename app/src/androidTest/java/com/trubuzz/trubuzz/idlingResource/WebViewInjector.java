package com.trubuzz.trubuzz.idlingResource;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.ComponentName;
import android.support.test.runner.lifecycle.ActivityLifecycleCallback;
import android.support.test.runner.lifecycle.Stage;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.utils.Find;

/**
 * Created by king on 2016/9/8.
 */
public class WebViewInjector implements ActivityLifecycleCallback {

    private final WebViewIdlingResource webViewIdlingResource;

    public WebViewInjector(WebViewIdlingResource webViewIdlingResource) {
        this.webViewIdlingResource = webViewIdlingResource;
    }

    @Override
    public void onActivityLifecycleChanged(Activity activity, Stage stage) {
        ComponentName websiteActivityComponentName =
                new ComponentName(activity, AName.MAIN);
        ComponentName an = activity.getComponentName();

        Log.i("jcd_onChanged",websiteActivityComponentName + " ; ComponentName = "+an);

        if (!an.equals(websiteActivityComponentName)) return;

        switch (stage) {
            case CREATED:
                // We need to wait for the activity to be created before getting a reference
                // to the webview
                Fragment webViewFragment =
                        activity.getFragmentManager().findFragmentById(Find.byShortId("scrollView"));

//                webViewIdlingResource.inject((WebView) webViewFragment.getView());
               FragmentManager af =  activity.getFragmentManager();
                View b = activity.getCurrentFocus();
                LoaderManager c =  activity.getLoaderManager();


                WebView v = (WebView) activity.findViewById(Find.byShortId("webview"));
                webViewIdlingResource.inject(activity,v);
                break;
            case STOPPED:
                // Clean up reference
                if (activity.isFinishing()) webViewIdlingResource.clear();
                break;
            default: // NOP
        }
    }
}