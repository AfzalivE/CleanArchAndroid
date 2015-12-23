package com.afzaln.cleanarch.data;

import javax.inject.Singleton;

import com.afzaln.cleanarch.app.AppComponent;
import dagger.Component;

/**
 * Created by afzal on 2015-12-13.
 */
@Component(
        dependencies = {AppComponent.class},
        modules = {DataModule.class}
)
@Singleton
public interface DataComponent {
    ApiaryService getApiaryService();
    AppModel getAppModel();
}
