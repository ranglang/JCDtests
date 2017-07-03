package com.trubuzz.trubuzz.feature.viewFirm;

import android.os.Looper;
import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.core.deps.guava.base.Predicate;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Provider;

import static android.support.test.espresso.util.TreeIterables.breadthFirstViewTraversal;
import static android.support.test.espresso.web.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.web.deps.guava.base.Preconditions.checkState;

/**
 * Created by king on 16/11/23.
 * same {@link android.support.test.espresso.base.ViewFinderImpl }
 */

public class ViewTracer {
    private  Matcher<View> viewMatcher;
    private  Provider<View> rootViewProvider;

    public ViewTracer(Matcher<View> viewMatcher, Provider<View> rootViewProvider) {
        this.viewMatcher = viewMatcher;
        this.rootViewProvider = rootViewProvider;
    }

    public List<View> getViews() throws AmbiguousViewMatcherException, NoMatchingViewException {
        checkMainThread();
        final Predicate<View> matcherPredicate = new ViewTracer.MatcherPredicateAdapter<View>(
                checkNotNull(viewMatcher));

        View root = rootViewProvider.get();
        Iterator<View> matchedViewIterator = Iterables.filter(
                breadthFirstViewTraversal(root),
                matcherPredicate).iterator();

        List<View> matchedViews = new ArrayList<>();
        while (matchedViewIterator.hasNext()) {
            matchedViews.add( matchedViewIterator.next());
        }
        return matchedViews;
    }

    private void checkMainThread() {
        checkState(Thread.currentThread().equals(Looper.getMainLooper().getThread()),
                "Executing a query on the view hierarchy outside of the main thread (on: %s)",
                Thread.currentThread().getName());
    }

    private static class MatcherPredicateAdapter<T> implements Predicate<T> {
        private final Matcher<? super T> matcher;

        private MatcherPredicateAdapter(Matcher<? super T> matcher) {
            this.matcher = checkNotNull(matcher);
        }

        @Override
        public boolean apply(T input) {
            return matcher.matches(input);
        }
    }

}
