package com.afzaln.cleanarch.questions;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import com.afzaln.cleanarch.domain.Question;
import com.afzaln.cleanarch.data.AppModel;
import com.afzaln.cleanarch.app.QuestionsScope;
import nz.bradcampbell.compartment.BasePresenter;
import rx.Observer;
import rx.Subscription;
import timber.log.Timber;

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
        loadQuestions(false);
    }

    public void loadQuestions(boolean forceNetworkRefresh) {
        // already subscribed, changes will be called in the Observer
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            return;
        }
        getView().showLoading();
        mSubscription = mAppModel.getQuestions(forceNetworkRefresh)
                .subscribe(mObserver);
    }

    private Observer<? super List<Question>> mObserver = new Observer<List<Question>>() {
        @Override
        public void onCompleted() {
            if (getView() != null) {
                getView().hideLoading();
            }
        }

        @Override
        public void onError(Throwable e) {
            Timber.e(e, "Error fetching questions");
            if (getView() != null) {
                getView().hideLoading();
                getView().showLoadingError();
            }
        }

        @Override
        public void onNext(List<Question> questions) {
            if (getView() != null) {
                getView().showQuestions((ArrayList<Question>) questions);
            }
        }
    };
}
