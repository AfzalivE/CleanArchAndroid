package com.afzaln.cleanarch.presenters;

import javax.inject.Inject;
import java.util.ArrayList;

import com.afzaln.cleanarch.domain.Question;
import com.afzaln.cleanarch.repo.AppModel;
import com.afzaln.cleanarch.scopes.QuestionsScope;
import com.afzaln.cleanarch.views.QuestionsView;
import nz.bradcampbell.compartment.BasePresenter;
import rx.Observer;
import rx.Subscription;

/**
 * Created by afzal on 2015-12-13.
 */
@QuestionsScope
public class QuestionsPresenter extends BasePresenter<QuestionsView> {

    @Inject AppModel mAppModel;
    private Subscription mSubscription;

    @Inject
    public QuestionsPresenter() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void loadQuestions() {
        // already subscribed, changes will be called in the Observer
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            return;
        }
        getView().showLoading();
        mSubscription = mAppModel.getQuestions()
                .subscribe(mObserver);
    }

    private Observer<? super ArrayList<Question>> mObserver = new Observer<ArrayList<Question>>() {
        @Override
        public void onCompleted() {
            if (getView() != null) {
                getView().hideLoading();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (getView() != null) {
                getView().hideLoading();
                getView().showLoadingError();
            }
        }

        @Override
        public void onNext(ArrayList<Question> questions) {
            if (getView() != null) {
                getView().showQuestions(questions);
            }
        }
    };
}
