package com.afzaln.cleanarch.app;

import android.content.Context;

import com.afzaln.cleanarch.CADaggerApp;
import dagger.Module;
import dagger.Provides;

/**
 * Created by afzal on 2015-12-13.
 */
@Module
public class AppModule {
    public static final String BASE_URL = "http://polls.apiblueprint.org/";

    private CADaggerApp mApp;

    public AppModule(CADaggerApp app) {
        mApp = app;
    }

    @Provides
    public CADaggerApp provideApp() {
        return mApp;
    }

    @Provides
    public Context provideAppContext() {
        return mApp.getApplicationContext();
    }
}
