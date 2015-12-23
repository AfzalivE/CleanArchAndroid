package com.afzaln.cleanarch.votes;

import com.afzaln.cleanarch.data.DataComponent;
import com.afzaln.cleanarch.questions.QuestionsScope;
import dagger.Component;
import nz.bradcampbell.compartment.HasPresenter;

/**
 * Created by afzal on 2015-12-15.
 */
@QuestionsScope
@Component(
        dependencies = {DataComponent.class}
)
public interface VoteComponent extends HasPresenter<VotePresenter> {
    void inject(VoteFragment fragment);
}