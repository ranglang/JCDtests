package com.trubuzz.trubuzz.shell.beautify;

import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.util.HumanReadables;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;

import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.utils.DoIt;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static com.trubuzz.trubuzz.utils.Judge.isVisible;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by king on 16/12/7.
 */

public class RecyclerElement implements Element<Matcher<View>> {
    private static final String TAG = "jcd_" + RecyclerElement.class.getSimpleName();

    private Matcher<View> recyclerMatcher;
    private int position;
    private Matcher<View> findMatcher;

    public RecyclerElement(Matcher<View> recyclerMatcher) {
        this.recyclerMatcher = recyclerMatcher;
    }
    public RecyclerElement(){}



    public static ViewAction atPositionAction(int position ,ViewAction action){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "atPositionAction performing ViewAction: " + action.getDescription()
                        + " on item at position: " + position;
            }

            @Override
            public void perform(UiController uiController, View view) {
                if(view instanceof RecyclerView){
                    Log.i(TAG, "perform: view instanceof RecyclerView");
                }
                RecyclerView recyclerView = (RecyclerView) view;
                uiController.loopMainThreadUntilIdle();
                scrollToPosition(recyclerView,position);    //滚动到该position
                int itemCount = recyclerView.getAdapter().getItemCount();
                Log.i(TAG, "perform: itemCount = " + itemCount);

                RecyclerView.ViewHolder viewHolderForPosition = recyclerView.findViewHolderForAdapterPosition(position);

                if (null == viewHolderForPosition) {
                    throw new PerformException.Builder().withActionDescription(this.toString())
                            .withViewDescription(HumanReadables.describe(view))
                            .withCause(new IllegalStateException("No view holder at position: " + position))
                            .build();
                }
                View viewAtPosition = viewHolderForPosition.itemView;
                if (null == viewAtPosition) {
                    throw new PerformException.Builder().withActionDescription(this.toString())
                            .withViewDescription(HumanReadables.describe(viewAtPosition))
                            .withCause(new IllegalStateException("No view at position: " + position)).build();
                }
                for(int i=0;i<5;i++){
                    if(! isVisible(viewAtPosition ,90)){
                        new SwipeUpToVisible().perform(uiController ,view);
                        break;
                    }
                }
                action.perform(uiController, viewAtPosition);
            }
        };
    }

    public static final class SwipeUpToVisible implements ViewAction{

        private static final float EDGE_FUZZ_FACTOR = 0.083f;

        @Override
        public Matcher<View> getConstraints() {
            return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
        }

        @Override
        public String getDescription() {
            return "swipe up to visible .";
        }

        @Override
        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;
            int childCount = recyclerView.getChildCount();
            for (int i = childCount - 1; i >= 0; i--) {
                View childAt = recyclerView.getChildAt(i);
                if (isVisible(childAt, 90)) {
                    new GeneralSwipeAction(Swipe.FAST,
                            DoIt.translate(GeneralLocation.BOTTOM_CENTER, 0, -EDGE_FUZZ_FACTOR),
                            GeneralLocation.TOP_CENTER, Press.FINGER)
                            .perform(uiController, childAt);

                    return;
                }
            }
        }
    }




    public static ViewAction scrollToRecyclerPosition(int position) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "scroll RecyclerView to position: " + position;
            }

            @Override
            public void perform(UiController uiController, View view) {
                scrollToPosition((RecyclerView) view,position);
//                RecyclerView recyclerView = (RecyclerView) view;
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                layoutManager.scrollToPositionWithOffset(position, 0);
//      //          recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, position);
            }
        };
    }

    private static void scrollToPosition(RecyclerView recyclerView ,int position){
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        layoutManager.scrollToPositionWithOffset(position, 0);
    }

    public Matcher<View> atPosition(int position){
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with RecyclerView position : " + position);
            }

            @Override
            protected boolean matchesSafely(View view) {
                ViewParent viewParent = view.getParent();
                if (!(viewParent instanceof RecyclerView))    return false;
                if (!recyclerMatcher.matches(viewParent)) return false;

                RecyclerView recyclerView = (RecyclerView) viewParent;
                View childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;

                return view == childView;
            }
        };
    }

    public Matcher<View> atMatcher(Matcher<View> matcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(View view) {
                if(! matcher.matches(view))     return false;
                ViewParent parent = view.getParent();
                while (parent != null) {
                    if((parent instanceof RecyclerView) &&  recyclerMatcher.matches(parent))
                        return true;
                    if(!(parent instanceof View))   return false;
                    view = (View) parent;
                    parent = view.getParent();
                }
                return false;
            }
        };

    }
    @Override
    public Matcher<View> interactionWay() {
        return allOf(this.atPosition(this.position),this.atMatcher(this.findMatcher));
    }

    public static int getPosition(final ViewInteraction viewInteraction ,Matcher<View> viewMatcher){
        final int[] position = {-1};
        viewInteraction.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "get RecyclerView`s position .";
            }

            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                int itemCount = recyclerView.getAdapter().getItemCount();
                for (int i = 0; i < itemCount; i++) {
                    View itemView = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                    if(viewMatcher.matches(itemView)){
                        position[0] = i;
                    }
                }
            }
        });
        return position[0];
    }


    /**
     * 获取 RecyclerView 在当前可见的最后一个child (可见度90)
     * @param viewInteraction 一个匹配了 RecyclerView 的 ViewInteraction
     * @return 最后一个child View.
     */
    public static View getVisibleView(final ViewInteraction viewInteraction){
        final View[] visibleView = {null};
        viewInteraction.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "get RecyclerView`s children , if current is visible 90% ";
            }

            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                int childCount = recyclerView.getChildCount();
                for (int i = childCount - 1; i >= 0; i--) {
                    View childAt = recyclerView.getChildAt(i);
                    if (isVisible(childAt, 90)) {
                        visibleView[0] = childAt;
                        Log.i(TAG, "getVisibleView: childCount =" + childCount + " ; at child : " + i);
                        return;
                    }
                }
            }
        });
        return visibleView[0];
    }


}
