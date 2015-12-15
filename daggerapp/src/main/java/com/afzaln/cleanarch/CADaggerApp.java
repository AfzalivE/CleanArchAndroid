package com.afzaln.cleanarch;

import android.content.Context;

import com.afzaln.cleanarch.components.AppComponent;
import com.afzaln.cleanarch.components.DaggerAppComponent;
import com.afzaln.cleanarch.components.DaggerRepoComponent;
import com.afzaln.cleanarch.components.RepoComponent;
import com.afzaln.cleanarch.debug.CrashReportingTree;
import com.afzaln.cleanarch.modules.AppModule;
import nz.bradcampbell.compartment.ComponentCacheApplication;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

/**
 * Created by afzal on 2015-12-07.
 */
public class CADaggerApp extends ComponentCacheApplication {
    private AppComponent mAppComponent;
    private RepoComponent mRepoComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    protected AppModule getAppModule() {
        return new AppModule(this);
    }

    public static AppComponent getAppComponent(Context context) {
        CADaggerApp app = (CADaggerApp) context.getApplicationContext();
        if (app.mAppComponent == null) {
            app.mAppComponent = DaggerAppComponent.builder()
                    .appModule(app.getAppModule())
                    .build();
        }

        return app.mAppComponent;
    }

    public static void cleanAppComponent(Context context) {
        CADaggerApp app = (CADaggerApp) context.getApplicationContext();
        app.mAppComponent = null;
    }

    public static RepoComponent getRepoComponent(Context context) {
        CADaggerApp app = (CADaggerApp) context.getApplicationContext();
        if (app.mRepoComponent == null) {
            app.mRepoComponent = DaggerRepoComponent.builder()
                    .appComponent(getAppComponent(context))
                    .build();
        }

        return app.mRepoComponent;
    }

    public static void cleanRepoComponent(Context context) {
        CADaggerApp app = (CADaggerApp) context.getApplicationContext();
        app.mRepoComponent = null;
    }
}
