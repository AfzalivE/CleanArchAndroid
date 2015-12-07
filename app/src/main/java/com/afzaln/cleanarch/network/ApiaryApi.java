package com.afzaln.cleanarch.network;

import java.util.List;

import com.afzaln.cleanarch.data.Question;
import retrofit.Callback;

/**
 * Api class to access Apiary APIs
 */
public class ApiaryApi extends Api {

    public void getTweets(Callback<List<Question>> callback) {
        getApiaryService().getQuestions().enqueue(callback);
    }
}
