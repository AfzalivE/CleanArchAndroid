package com.afzaln.cleanarch.data;

import java.util.List;

import com.afzaln.cleanarch.domain.Choice;
import com.afzaln.cleanarch.domain.Question;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by afzal on 2015-12-23.
 */
public class NetworkApi {

    private final ApiaryService mApiaryService;
    private final DatabaseApi mDatabaseApi;

    public NetworkApi(ApiaryService apiaryService, DatabaseApi databaseApi) {
        mApiaryService = apiaryService;
        mDatabaseApi = databaseApi;
    }

    public Observable<List<Question>> getQuestions() {
        return mApiaryService.getQuestions()
                .subscribeOn(Schedulers.io())
                .flatMapIterable(questions -> questions)
                .doOnNext(question -> mDatabaseApi.saveQuestion(question))
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .toList();
    }

    public Observable<Question> getQuestion(int questionId) {
        return mApiaryService.getQuestion(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Choice> submitVote(int questionId, int choiceId) {
        return mApiaryService.postQuestionResponse(questionId, choiceId)
                .subscribeOn(Schedulers.io())
                .doOnNext(choice -> mDatabaseApi.updateChoice(choice))
                .observeOn(AndroidSchedulers.mainThread());
    }
}
