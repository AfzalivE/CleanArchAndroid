package com.afzaln.cleanarch.network;

import java.util.List;

import com.afzaln.cleanarch.data.Choice;
import com.afzaln.cleanarch.data.Question;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Retrofit interface for Apiary questions API
 */
public interface ApiaryService {

    @GET("questions")
    Call<List<Question>> getQuestions();

    @GET("questions/{question_id}")
    Call<Question> getQuestion(@Path("question_id") int questionId);

    @POST("questions/{question_id}/choices/{choice_id}")
    Call<Choice> postQuestionResponse(@Path("question_id") int questionId, @Path("choice_id") int choiceId);
}
