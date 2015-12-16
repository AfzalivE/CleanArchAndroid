package com.afzaln.cleanarch.presenters;

import javax.inject.Inject;

import com.afzaln.cleanarch.domain.Choice;
import com.afzaln.cleanarch.domain.Question;
import com.afzaln.cleanarch.repo.AppModel;
import com.afzaln.cleanarch.scopes.QuestionsScope;
import com.afzaln.cleanarch.views.VoteView;
import nz.bradcampbell.compartment.BasePresenter;
import rx.Observer;
import rx.Subscription;

/**
 * Created by afzal on 2015-12-15.
 */
@QuestionsScope
public class VotePresenter extends BasePresenter<VoteView> {
    @Inject AppModel mAppModel;
    private Subscription mQuestionSubscription;
    private Subscription mVoteSubscription;

    @Inject
    public VotePresenter() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVoteSubscription != null) {
            mVoteSubscription.unsubscribe();
        }
        if (mQuestionSubscription != null) {
            mQuestionSubscription.unsubscribe();
        }
    }

    public void loadQuestion(int questionId) {
        // already subscribed, changes will be called in the Observer
        if (mQuestionSubscription != null && !mQuestionSubscription.isUnsubscribed()) {
            return;
        }

        getView().showQuestionLoading();
        mQuestionSubscription = mAppModel.getQuestion(questionId)
                .subscribe(mQuestionObserver);
    }

    public void submitVote(Question question, Choice choice) {
        // already subscribed, changes will be called in the Observer
        if (mVoteSubscription != null && !mVoteSubscription.isUnsubscribed()) {
            return;
        }

        getView().showVoteLoading();

        mVoteSubscription = mAppModel.submitVote(question.getId(), choice.getId())
                .subscribe(mVoteObserver);
    }

    private Observer<? super Question> mQuestionObserver = new Observer<Question>() {
        @Override
        public void onCompleted() {
            if (getView() != null) {
                getView().hideQuestionLoading();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (getView() != null) {
                getView().showQuestionError();
            }
        }

        @Override
        public void onNext(Question question) {
            if (getView() != null) {
                getView().showQuestion(question);
            }
        }
    };

    private Observer<? super Choice> mVoteObserver = new Observer<Choice>() {
        @Override
        public void onCompleted() {
            if (getView() != null) {
                getView().hideVoteLoading();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (getView() != null) {
//                getView().hideVoteLoading();
                getView().showVoteError();
            }
        }

        @Override
        public void onNext(Choice choice) {
            if (getView() != null) {
                getView().updateVoteCount(choice);
            }
        }
    };
}
