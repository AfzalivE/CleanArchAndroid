package com.afzaln.cleanarch.components;

import com.afzaln.cleanarch.fragments.QuestionsFragment;
import com.afzaln.cleanarch.presenters.QuestionsPresenter;
import com.afzaln.cleanarch.scopes.QuestionsScope;
import dagger.Component;
import nz.bradcampbell.compartment.HasPresenter;

/**
 * Created by afzal on 2015-12-13.
 */
@QuestionsScope
@Component(
        dependencies = {RepoComponent.class}
)
public interface QuestionsComponent extends HasPresenter<QuestionsPresenter> {
    void inject(QuestionsFragment fragment);
}
