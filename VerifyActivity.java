package com.example.arduinosensors;

import android.app.Application;

/**
 * Class responsible for checking if the activity is onResume or onPause
 */
public class VerifyActivity extends Application {

    private static boolean activityVisible;

    /**
     * Getter for activityVisible
     *
     * @return activityVisible
     */
    public static boolean isActivityVisible() {
        return activityVisible;
    }

    /**
     * Set activityVisible to true
     */
    public static void activityResumed() {
        activityVisible = true;
    }

    /**
     * Set activityVisible to false
     */
    public static void activityPaused() {
        activityVisible = false;
    }


}
