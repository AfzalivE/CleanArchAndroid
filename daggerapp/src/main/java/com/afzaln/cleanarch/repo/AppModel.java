package com.afzaln.cleanarch.repo;

import java.util.ArrayList;

import com.afzaln.cleanarch.domain.Choice;
import com.afzaln.cleanarch.domain.Question;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by afzal on 2015-12-13.
 */
public class AppModel {

    private final ApiaryService mApiaryService;

    public AppModel(ApiaryService apiaryService) {
        mApiaryService = apiaryService;
    }

    public Observable<ArrayList<Question>> getQuestions() {
        return mApiaryService.getQuestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Choice> submitVote(int questionId, int choiceId) {
        return mApiaryService.postQuestionResponse(questionId, choiceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Question> getQuestion(int questionId) {
        return mApiaryService.getQuestion(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
