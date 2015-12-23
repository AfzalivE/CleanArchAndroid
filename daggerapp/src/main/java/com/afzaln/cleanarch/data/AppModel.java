package com.afzaln.cleanarch.data;

import java.util.List;

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
    private final DatabaseHelper mDatabaseHelper;

    public AppModel(ApiaryService apiaryService, DatabaseHelper databaseHelper) {
        mApiaryService = apiaryService;
        mDatabaseHelper = databaseHelper;
    }

    public Observable<List<Question>> getQuestions() {
        return Observable.concat(getLocalQuestions(), getNetworkQuestions())
                .first(questions -> !questions.isEmpty());
    }

    public Observable<List<Question>> getLocalQuestions() {
        return mDatabaseHelper.getQuestions();
    }

    public Observable<List<Question>> getNetworkQuestions() {
        return mApiaryService.getQuestions()
                .subscribeOn(Schedulers.io())
                .flatMapIterable(questions -> questions)
                .doOnNext(question -> mDatabaseHelper.saveQuestion(question))
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .toList();
    }

    public Observable<Question> getQuestion(int questionId) {
        return Observable.concat(getLocalQuestion(questionId), getNetworkQuestion(questionId))
                .first(question -> question != null && question.choices != null && !question.choices.isEmpty());
    }

    private Observable<Question> getLocalQuestion(int questionId) {
        return mDatabaseHelper.getQuestion(questionId);
    }

    public Observable<Question> getNetworkQuestion(int questionId) {
        return mApiaryService.getQuestion(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Choice> submitVote(int questionId, int choiceId) {
        return mApiaryService.postQuestionResponse(questionId, choiceId)
                .subscribeOn(Schedulers.io())
                .doOnNext(choice -> mDatabaseHelper.saveChoice(choice))
                .observeOn(AndroidSchedulers.mainThread());
    }
}
