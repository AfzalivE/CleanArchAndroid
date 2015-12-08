package com.afzaln.cleanarch.presenters;

import com.afzaln.cleanarch.data.Choice;
import com.afzaln.cleanarch.data.Question;

/**
 * Created by afzal on 2015-12-07.
 */
public interface VotePresenter {
    void onVoteSubmit(Question question, String choiceUrl);
    void onVoteSuccess(Choice choice);
    void onVoteFailure();
    void showQuestionItem(Question question);
    void onReceivedQuestion(Question question);
    void onQuestionFailure();
}
