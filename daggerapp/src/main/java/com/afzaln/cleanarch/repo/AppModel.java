package com.afzaln.cleanarch.repo;

import java.util.ArrayList;

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
}
