package com.afzaln.cleanarch.util;

import android.util.Log;

import timber.log.Timber.Tree;

/**
 * Created by afzal on 2015-12-07.
 */
public class CrashReportingTree extends Tree {

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }

        // log error in crashlytics here
//        FakeCrashLibrary.log(priority, tag, message);

        if (t != null) {
            if (priority == Log.ERROR) {
                // log error in crashlytics here
//                FakeCrashLibrary.logError(t);
            } else if (priority == Log.WARN) {
                // log error in crashlytics here
//                FakeCrashLibrary.logWarning(t);
            }

        }
    }
}
