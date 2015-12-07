package com.afzaln.cleanarch.interactors;

import java.util.List;

import com.afzaln.cleanarch.data.Question;
import com.afzaln.cleanarch.presenters.QuestionsPresenter;

/**
 * Created by afzal on 2015-12-07.
 */
public interface QuestionsInteractor {
    void loadQuestions(QuestionsPresenter questionsPresenter);
    void returnQuestions(List<Question> questions);
    void returnFailure();
}
