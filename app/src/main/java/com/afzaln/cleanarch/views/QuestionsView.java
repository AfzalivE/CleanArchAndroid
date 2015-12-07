package com.afzaln.cleanarch.views;

import java.util.ArrayList;

import com.afzaln.cleanarch.data.Question;

/**
 * Created by afzal on 2015-12-07.
 */
public interface QuestionsView {
    void showQuestions(ArrayList<Question> questions);
    void onQuestionClicked(int position);
}
