package com.afzaln.cleanarch.components;

import com.afzaln.cleanarch.fragments.VoteFragment;
import com.afzaln.cleanarch.presenters.VotePresenter;
import com.afzaln.cleanarch.scopes.QuestionsScope;
import dagger.Component;
import nz.bradcampbell.compartment.HasPresenter;

/**
 * Created by afzal on 2015-12-15.
 */
@QuestionsScope
@Component(
        dependencies = {RepoComponent.class}
)
public interface VoteComponent extends HasPresenter<VotePresenter> {
    void inject(VoteFragment fragment);
}