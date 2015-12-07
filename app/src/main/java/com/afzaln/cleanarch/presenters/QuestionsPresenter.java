package com.afzaln.cleanarch.presenters;

import java.util.ArrayList;

import com.afzaln.cleanarch.data.Question;

/**
 * Created by afzal on 2015-12-07.
 */
public interface QuestionsPresenter {
    void onQuestionsLoaded(ArrayList<Question> questions);
    void onQuestionClicked(Question question);
    void onQuestionsLoadFailed();
    boolean onBackPressed();
}
