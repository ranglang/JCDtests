package com.trubuzz.trubuzz.feature.custom;

import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.PrecisionDescriber;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.action.Swiper;
import android.support.test.espresso.util.HumanReadables;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;

import com.trubuzz.trubuzz.constant.Direction;

import org.hamcrest.Matcher;

import java.util.Arrays;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static com.trubuzz.trubuzz.constant.Config.VISIBILITY;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withAny;
import static com.trubuzz.trubuzz.utils.God.getScreenRectangle;
import static com.trubuzz.trubuzz.utils.Judge.isVisible;

/**
 * Created by king on 16/10/24.
 */

public class CustomViewAction  {
    private static final String TAG = "jcd_" + CustomViewAction.class.getSimpleName();

    private CustomViewAction(){}

   public static ViewAction nothing(){
       return new ViewAction() {
           @Override
           public Matcher<View> getConstraints() {
               return isDisplayed();
           }

           @Override
           public String getDescription() {
               return "nothing to do .";
           }

           @Override
           public void perform(UiController uiController, View view) {
               Log.i(TAG, "perform: no thing to do .");
           }
       };
   }

    public static ViewAction swipeUpAs(int distance,boolean checkVisible){
        return new SwipeAsDirection(Swipe.FAST, Direction.UP ,10 ,distance ,Press.FINGER, checkVisible);
    }
    public static ViewAction swipeUpAs(){
        return swipeUpAs(100,false);
    }
    public static ViewAction swipeDownAs(int distance,boolean checkVisible){
        return new SwipeAsDirection(Swipe.FAST, Direction.DOWN ,500 ,distance ,Press.FINGER, checkVisible);
    }
    public static ViewAction swipeDownAs(){
        return swipeDownAs(100,false);
    }
    public static ViewAction swipeLeftAs(int distance,boolean checkVisible){
        return new SwipeAsDirection(Swipe.FAST, Direction.LEFT,100 ,distance ,Press.FINGER, checkVisible);
    }
    public static ViewAction swipeLeftAs(){
        return swipeLeftAs(100,false);
    }
    public static ViewAction swipeRightAs(int distance,boolean checkVisible){
        return new SwipeAsDirection(Swipe.FAST, Direction.RIGHT ,100 ,distance ,Press.FINGER, checkVisible);
    }
    public static ViewAction swipeRightAs(){
        return swipeRightAs(100,false);
    }

    public static ViewAction swipeAs( Direction direction, int fuzz, int swipe_distance, boolean checkVisible){
        return new SwipeAsDirection(Swipe.FAST, direction, fuzz, swipe_distance, Press.FINGER, checkVisible);
    }
//    public static final class SwipeUpToVisible implements ViewAction{
//
//        /** 允许距离屏幕边缘的点位 */
//        private static final int FUZZ = 10;
//        /** 滑动的距离 */
////        private static final int swipe_distance = 100;
//
//        private final Swiper swiper;
//        private final PrecisionDescriber precisionDescriber;
//        private final int swipe_distance;
//
//
//        public SwipeUpToVisible(Swiper swiper, PrecisionDescriber precisionDescriber, int swipe_distance) {
//            this.swiper = swiper;
//            this.precisionDescriber = precisionDescriber;
//            this.swipe_distance = swipe_distance;
//        }
//
//        @Override
//        public Matcher<View> getConstraints() {
//            return withAny();
//        }
//
//        @Override
//        public String getDescription() {
//            return "to visible .";
//        }
//
//        @Override
//        public void perform(UiController uiController, View view) {
////            if (isVisible(view,VISIBILITY)) return;
//
//            float[] precision = precisionDescriber.describePrecision();
//            float[][] positions = Direction.UP.getPosition(10, 100, getScreenRectangle(view));
//
//            new SwipeAs(swiper, positions[0], positions[1], precision)
//                    .perform(uiController, view);
//
//        }
//
//
//    }

    /**
     * 按指定的方向,距离等滑动.
     */
    public static final class SwipeAsDirection implements ViewAction{
        private final Swiper swiper;
        private final Direction direction;
        private final int fuzz;
        private final int swipe_distance;
        private final PrecisionDescriber precisionDescriber;
        private final boolean checkVisible;

        public SwipeAsDirection(Swiper swiper, Direction direction, int fuzz, int swipe_distance,
                                PrecisionDescriber precisionDescriber, boolean checkVisible) {
            this.swiper = swiper;
            this.direction = direction;
            this.fuzz = fuzz;
            this.swipe_distance = swipe_distance;
            this.precisionDescriber = precisionDescriber;
            this.checkVisible = checkVisible;
        }

        @Override

        public Matcher<View> getConstraints() {
            return withAny();
        }

        @Override
        public String getDescription() {
            return "swipe as " + direction;
        }

        @Override
        public void perform(UiController uiController, View view) {
            if(checkVisible){
                if(isVisible(view , VISIBILITY)) return;
            }
            float[] precision = precisionDescriber.describePrecision();
            float[][] positions = direction.getPosition(fuzz, swipe_distance, getScreenRectangle(view));

            new SwipeAs(swiper, positions[0], positions[1], precision)
                    .perform(uiController, view);
        }
    }

    public static final class SwipeAs implements ViewAction{
        /** Maximum number of times to attempt sending a swipe action. */
        private static final int MAX_TRIES = 3;

        private final Swiper swiper;
        private final float[] startCoordinates;
        private final float[] endCoordinates;
        private final float[] precision ;

        public SwipeAs(Swiper swiper, float[] startCoordinates, float[] endCoordinates, float[] precision) {
            this.swiper = swiper;
            this.startCoordinates = startCoordinates;
            this.endCoordinates = endCoordinates;
            this.precision = precision;
        }

        @Override
        public Matcher<View> getConstraints() {
            return withAny();
        }

        @Override
        public String getDescription() {
            return "swipe .";
        }

        @Override
        public void perform(UiController uiController, View view) {
            Swiper.Status status = Swiper.Status.FAILURE;

            for (int tries = 0; tries < MAX_TRIES && status != Swiper.Status.SUCCESS; tries++) {
                try {
                    Log.i(TAG, "SwipeAs perform .swipe as : "+ Arrays.toString(startCoordinates)
                            + " -> "+ Arrays.toString(endCoordinates) +
                            " ; precision : "+ Arrays.toString(precision));

                    status = swiper.sendSwipe(uiController, startCoordinates, endCoordinates, precision);
                } catch (RuntimeException re) {
                    throw new PerformException.Builder()
                            .withActionDescription(this.getDescription())
                            .withViewDescription(HumanReadables.describe(view))
                            .withCause(re)
                            .build();
                }

                int duration = ViewConfiguration.getPressedStateDuration();
                // ensures that all work enqueued to process the swipe has been run.
                if (duration > 0) {
                    uiController.loopMainThreadForAtLeast(duration);
                }
            }

            if (status == Swiper.Status.FAILURE) {
                throw new PerformException.Builder()
                        .withActionDescription(getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new RuntimeException(String.format(
                                "Couldn't swipe from: %s,%s to: %s,%s precision: %s, %s . Swiper: %s "
                                        + "start coordinate provider: %s . Tried %s times",
                                startCoordinates[0],
                                startCoordinates[1],
                                endCoordinates[0],
                                endCoordinates[1],
                                precision[0],
                                precision[1],
                                swiper,
                                Arrays.toString(startCoordinates),
                                MAX_TRIES)))
                        .build();
            }
        }
    }
}
