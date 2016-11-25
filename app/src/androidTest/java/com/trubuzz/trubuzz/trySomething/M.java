package com.trubuzz.trubuzz.trySomething;

import android.support.test.espresso.action.CoordinatesProvider;
import android.view.View;

/**
 * Created by king on 16/11/22.
 */

public enum  M implements CoordinatesProvider {

    LEFT{
        @Override
        public float[] calculateCoordinates (View view){
            return new float[0];
        }
    }
}
