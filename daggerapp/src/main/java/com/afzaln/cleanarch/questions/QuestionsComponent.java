package com.afzaln.cleanarch.questions;

import com.afzaln.cleanarch.data.DataComponent;
import dagger.Component;
import nz.bradcampbell.compartment.HasPresenter;

/**
 * Created by afzal on 2015-12-13.
 */
@QuestionsScope
@Component(
        dependencies = {DataComponent.class}
)
public interface QuestionsComponent extends HasPresenter<QuestionsPresenter> {
    void inject(QuestionsFragment fragment);
}
