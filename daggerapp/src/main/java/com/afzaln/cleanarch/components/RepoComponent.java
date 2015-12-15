package com.afzaln.cleanarch.components;

import javax.inject.Singleton;

import com.afzaln.cleanarch.modules.RepoModule;
import com.afzaln.cleanarch.repo.ApiaryService;
import com.afzaln.cleanarch.repo.AppModel;
import dagger.Component;

/**
 * Created by afzal on 2015-12-13.
 */
@Component(
        dependencies = {AppComponent.class},
        modules = {RepoModule.class}
)
@Singleton
public interface RepoComponent {
    ApiaryService getApiaryService();
    AppModel getAppModel();
}
