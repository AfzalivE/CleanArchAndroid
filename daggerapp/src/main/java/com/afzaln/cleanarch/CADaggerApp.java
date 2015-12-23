package com.afzaln.cleanarch;

import android.content.Context;

import com.afzaln.cleanarch.app.AppComponent;
import com.afzaln.cleanarch.app.AppModule;
import com.afzaln.cleanarch.app.DaggerAppComponent;
import com.afzaln.cleanarch.data.DaggerDataComponent;
import com.afzaln.cleanarch.data.DataComponent;
import com.afzaln.cleanarch.debug.CrashReportingTree;
import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowManager;
import nz.bradcampbell.compartment.ComponentCacheApplication;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

/**
 * Created by afzal on 2015-12-07.
 */
public class CADaggerApp extends ComponentCacheApplication {
    private AppComponent mAppComponent;
    private DataComponent mRepoComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
//            OkHttpClient client = new OkHttpClient();
//            client.networkInterceptors().add(new StethoInterceptor());
            Stetho.initializeWithDefaults(this);
        } else {
            Timber.plant(new CrashReportingTree());
        }
        FlowManager.init(this);
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

    public static DataComponent getDataComponent(Context context) {
        CADaggerApp app = (CADaggerApp) context.getApplicationContext();
        if (app.mRepoComponent == null) {
            app.mRepoComponent = DaggerDataComponent.builder()
                    .appComponent(getAppComponent(context))
                    .build();
        }

        return app.mRepoComponent;
    }

    public static void cleanDataComponent(Context context) {
        CADaggerApp app = (CADaggerApp) context.getApplicationContext();
        app.mRepoComponent = null;
    }
}
