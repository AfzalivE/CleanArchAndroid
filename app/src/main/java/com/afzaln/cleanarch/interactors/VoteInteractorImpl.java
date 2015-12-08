package com.afzaln.cleanarch.interactors;

import java.lang.ref.WeakReference;

import com.afzaln.cleanarch.data.Choice;
import com.afzaln.cleanarch.data.Question;
import com.afzaln.cleanarch.network.ApiaryApi;
import com.afzaln.cleanarch.presenters.VotePresenter;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by afzal on 2015-12-07.
 */
public class VoteInteractorImpl implements VoteInteractor {
    private ApiaryApi mApiaryApi;
    private Callback<Choice> mVoteCallback = new Callback<Choice>() {
        @Override
        public void onResponse(Response<Choice> response, Retrofit retrofit) {
            returnVoteSuccess(response.body());
        }

        @Override
        public void onFailure(Throwable t) {
            returnVoteFailure();
        }
    };

    private Callback<Question> mQuestionCallback = new Callback<Question>() {
        @Override
        public void onResponse(Response<Question> response, Retrofit retrofit) {
            returnQuestion(response.body());
        }

        @Override
        public void onFailure(Throwable t) {
            returnVoteFailure();
        }
    };

    WeakReference<VotePresenter> wVotePresenter;

    public void getQuestion(VotePresenter presenter, Question question) {
        wVotePresenter = new WeakReference<>(presenter);
        String[] questionUrl = question.url.split("/");
        int questionId = Integer.parseInt(questionUrl[questionUrl.length - 1]);

        getApiaryApi().getQuestion(questionId, mQuestionCallback);
    }

    @Override
    public void returnQuestion(Question question) {
        VotePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onReceivedQuestion(question);
            wVotePresenter.clear();
        }
    }

    @Override
    public void returnQuestionFailure() {
        VotePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onQuestionFailure();
            wVotePresenter.clear();
        }
    }

    @Override
    public void vote(VotePresenter presenter, Question question, String choiceUrl) {
        wVotePresenter = new WeakReference<>(presenter);
        String[] questionUrl = question.url.split("/");
        int questionId = Integer.parseInt(questionUrl[questionUrl.length - 1]);
        String[] choiceUrlArray = choiceUrl.split("/");
        int choiceUrlId = Integer.parseInt(choiceUrlArray[choiceUrlArray.length - 1]);

        getApiaryApi().vote(questionId, choiceUrlId, mVoteCallback);
    }

    @Override
    public void returnVoteSuccess(Choice choice) {
        VotePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onVoteSuccess(choice);
            wVotePresenter.clear();
        }
    }

    @Override
    public void returnVoteFailure() {
        VotePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onVoteFailure();
            wVotePresenter.clear();
        }
    }

    private VotePresenter getPresenter() {
        if (wVotePresenter != null) {
            return wVotePresenter.get();
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
