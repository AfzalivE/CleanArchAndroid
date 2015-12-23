package com.afzaln.cleanarch.questions;

import java.util.ArrayList;

import com.afzaln.cleanarch.domain.Question;

/**
 * Created by afzal on 2015-12-13.
 */
public interface QuestionsView {
    void showQuestions(ArrayList<Question> questions);
    void onQuestionClicked(int position);
    void showLoading();
    void hideLoading();
    void showLoadingError();
}
