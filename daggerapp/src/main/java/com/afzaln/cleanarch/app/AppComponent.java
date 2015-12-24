package com.afzaln.cleanarch.app;

import com.afzaln.cleanarch.CADaggerApp;
import com.afzaln.cleanarch.QuestionsActivity;
import dagger.Component;

/**
 * Created by afzal on 2015-12-13.
 */
@Component(
        modules = {AppModule.class}
)
public interface AppComponent {
    QuestionsActivity inject(QuestionsActivity activity);
    CADaggerApp getApp();
}
