package com.afzaln.cleanarch.app;

import android.content.Context;

import com.afzaln.cleanarch.CADaggerApp;
import com.afzaln.cleanarch.MainActivity;
import dagger.Component;

/**
 * Created by afzal on 2015-12-13.
 */
@Component(
        modules = {AppModule.class}
)
public interface AppComponent {
    MainActivity inject(MainActivity activity);
    Context getAppContext();
    CADaggerApp getApp();
}
