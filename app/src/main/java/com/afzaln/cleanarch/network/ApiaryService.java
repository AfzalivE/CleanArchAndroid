package com.afzaln.cleanarch.network;

import java.util.List;

import com.afzaln.cleanarch.data.Question;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Retrofit interface for Apiary questions API
 */
public interface ApiaryService {

    @GET("questions")
    Call<List<Question>> getQuestions();
}
