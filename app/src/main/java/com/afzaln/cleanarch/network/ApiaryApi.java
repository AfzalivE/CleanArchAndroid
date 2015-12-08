package com.afzaln.cleanarch.network;

import java.util.List;

import com.afzaln.cleanarch.data.Choice;
import com.afzaln.cleanarch.data.Question;
import retrofit.Callback;

/**
 * Api class to access Apiary APIs
 */
public class ApiaryApi extends Api {

    public void getQuestions(Callback<List<Question>> callback) {
        getApiaryService().getQuestions().enqueue(callback);
    }

    public void getQuestion(int questionId, Callback<Question> callback) {
        getApiaryService().getQuestion(questionId).enqueue(callback);
    }

    public void vote(int questionId, int choiceId, Callback<Choice> callback) {
        getApiaryService().postQuestionResponse(questionId, choiceId).enqueue(callback);
    }
}
