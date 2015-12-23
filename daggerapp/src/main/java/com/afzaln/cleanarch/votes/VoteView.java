package com.afzaln.cleanarch.votes;

import com.afzaln.cleanarch.models.Choice;
import com.afzaln.cleanarch.models.Question;

/**
 * Created by afzal on 2015-12-15.
 */
public interface VoteView {
    void showQuestion(Question question);
    void showQuestionLoading();
    void hideVoteLoading();
    void showVoteLoading();
    void showVoteError();
    void updateVoteCount(Choice choice);
    void hideQuestionLoading();
    void showQuestionError();
}
