package com.afzaln.cleanarch.interactors;

import com.afzaln.cleanarch.data.Question;
import com.afzaln.cleanarch.presenters.VotePresenter;

/**
 * Created by afzal on 2015-12-07.
 */
public interface VoteInteractor {
    void vote(VotePresenter presenter, Question question, String choiceUrl);
}
