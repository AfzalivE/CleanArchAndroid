package com.afzaln.cleanarch;

import android.app.Application;
import android.content.Context;

import com.afzaln.cleanarch.timber.CrashReportingTree;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

/**
 * Created by afzal on 2015-12-07.
 */
public class CleanArchApp extends Application {

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        mRefWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        CleanArchApp application = (CleanArchApp) context.getApplicationContext();
        return application.mRefWatcher;
    }
}
