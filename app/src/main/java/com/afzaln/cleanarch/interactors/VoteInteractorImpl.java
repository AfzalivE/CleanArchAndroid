package com.afzaln.cleanarch.interactors;

import java.lang.ref.WeakReference;

import com.afzaln.cleanarch.data.Choice;
import com.afzaln.cleanarch.data.Question;
import com.afzaln.cleanarch.presenters.VotePresenter;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by afzal on 2015-12-07.
 */
public class VoteInteractorImpl extends Interactor implements VoteInteractor {
    WeakReference<VotePresenter> wVotePresenter;

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

    @Override
    public void vote(VotePresenter presenter, Question question, String choiceUrl) {
        wVotePresenter = new WeakReference<>(presenter);
        String[] questionUrl = question.url.split("/");
        int questionId = Integer.parseInt(questionUrl[questionUrl.length - 1]);
        String[] choiceUrlArray = choiceUrl.split("/");
        int choiceUrlId = Integer.parseInt(choiceUrlArray[choiceUrlArray.length - 1]);

        getApiaryApi().vote(questionId, choiceUrlId, mVoteCallback);
    }

    public void returnVoteSuccess(Choice choice) {
        VotePresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.onVoteSuccess(choice);
            wVotePresenter.clear();
        }
    }

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
}
