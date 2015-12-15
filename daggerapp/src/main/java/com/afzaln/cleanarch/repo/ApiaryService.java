package com.afzaln.cleanarch.repo;

import java.util.ArrayList;

import com.afzaln.cleanarch.domain.Choice;
import com.afzaln.cleanarch.domain.Question;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Retrofit interface for Apiary questions API
 */
public interface ApiaryService {

    @GET("questions")
    Observable<ArrayList<Question>> getQuestions();

    @GET("questions/{question_id}")
    Observable<Question> getQuestion(@Path("question_id") int questionId);

    @POST("questions/{question_id}/choices/{choice_id}")
    Observable<Choice> postQuestionResponse(@Path("question_id") int questionId, @Path("choice_id") int choiceId);
}
