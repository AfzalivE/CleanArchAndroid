package com.afzaln.cleanarch.votes.ui;

import com.afzaln.cleanarch.data.DataComponent;
import com.afzaln.cleanarch.app.QuestionsScope;
import com.afzaln.cleanarch.votes.VotePresenter;
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