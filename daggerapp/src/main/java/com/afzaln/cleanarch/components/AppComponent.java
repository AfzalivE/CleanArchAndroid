package com.afzaln.cleanarch.components;

import com.afzaln.cleanarch.CADaggerApp;
import com.afzaln.cleanarch.MainActivity;
import com.afzaln.cleanarch.modules.AppModule;
import dagger.Component;

/**
 * Created by afzal on 2015-12-13.
 */
@Component(
        modules = {AppModule.class}
)
public interface AppComponent {
    MainActivity inject(MainActivity activity);
    CADaggerApp getApp();
}
