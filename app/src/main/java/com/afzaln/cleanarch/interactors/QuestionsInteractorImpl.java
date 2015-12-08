package com.afzaln.cleanarch.interactors;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.afzaln.cleanarch.data.Question;
import com.afzaln.cleanarch.network.ApiaryApi;
import com.afzaln.cleanarch.presenters.QuestionsPresenter;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by afzal on 2015-12-07.
 */
public class QuestionsInteractorImpl implements QuestionsInteractor {

    ApiaryApi mApiaryApi;

    private Callback<List<Question>> mGetQuestionsCallback = new Callback<List<Question>>() {
        @Override
        public void onResponse(Response<List<Question>> response, Retrofit retrofit) {
            List<Question> questions = response.body();
            returnQuestions(questions);
        }

        @Override
        public void onFailure(Throwable t) {
            returnFailure();
        }
    };

    WeakReference<QuestionsPresenter> wQuestionsPresenter;

    @Override
    public void loadQuestions(QuestionsPresenter questionsPresenter) {
        wQuestionsPresenter = new WeakReference<>(questionsPresenter);
        getApiaryApi().getQuestions(mGetQuestionsCallback);
    }

    @Override
    public void returnQuestions(List<Question> questions) {
        QuestionsPresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onQuestionsLoaded((ArrayList<Question>) questions);
            wQuestionsPresenter.clear();
        }
    }

    @Override
    public void returnFailure() {
        QuestionsPresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onQuestionsLoadFailed();
            wQuestionsPresenter.clear();
        }
    }

    private QuestionsPresenter getPresenter() {
        if (wQuestionsPresenter != null) {
            return wQuestionsPresenter.get();
        }

        return null;
    }

    private ApiaryApi getApiaryApi() {
        if (mApiaryApi == null) {
            mApiaryApi = new ApiaryApi();
        }

        return mApiaryApi;
    }
}
