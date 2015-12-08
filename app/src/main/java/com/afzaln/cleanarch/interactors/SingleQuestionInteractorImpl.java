package com.afzaln.cleanarch.interactors;

import java.lang.ref.WeakReference;

import com.afzaln.cleanarch.data.Question;
import com.afzaln.cleanarch.presenters.VotePresenter;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by afzal on 2015-12-07.
 */
public class SingleQuestionInteractorImpl extends Interactor implements SingleQuestionInteractor {
    WeakReference<VotePresenter> wVotePresenter;

    @Override
    public void getQuestion(VotePresenter presenter, Question question) {
        wVotePresenter = new WeakReference<>(presenter);
        String[] questionUrl = question.url.split("/");
        int questionId = Integer.parseInt(questionUrl[questionUrl.length - 1]);

        getApiaryApi().getQuestion(questionId, mQuestionCallback);
    }

    private Callback<Question> mQuestionCallback = new Callback<Question>() {
        @Override
        public void onResponse(Response<Question> response, Retrofit retrofit) {
            returnQuestion(response.body());
        }

        @Override
        public void onFailure(Throwable t) {
            returnQuestionFailure();
        }
    };

    public void returnQuestion(Question question) {
        VotePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onReceivedQuestion(question);
            wVotePresenter.clear();
        }
    }

    public void returnQuestionFailure() {
        VotePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onQuestionFailure();
            wVotePresenter.clear();
        }
    }

    private VotePresenter getPresenter() {
        if (wVotePresenter != null) {
            return wVotePresenter.get();
        }

        return null;
    }
}
