package com.afzaln.cleanarch.presenters;

import java.util.ArrayList;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.afzaln.cleanarch.R;
import com.afzaln.cleanarch.data.Question;
import com.afzaln.cleanarch.interactors.QuestionsInteractorImpl;
import com.afzaln.cleanarch.views.QuestionDetailView;
import com.afzaln.cleanarch.views.QuestionsView;
import com.afzaln.cleanarch.views.QuestionsViewImpl;

/**
 * Created by afzal on 2015-12-07.
 */
public class QuestionsPresenterImpl extends LinearLayout implements QuestionsPresenter {

    @Bind(R.id.questions_layout)
    QuestionsView mQuestionsView;

    private final QuestionsInteractorImpl mQuestionInteractor;

    public QuestionsPresenterImpl(Context context, AttributeSet attrs) {
        super(context, attrs);

        mQuestionInteractor = new QuestionsInteractorImpl();
        mQuestionInteractor.loadQuestions(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
        mQuestionInteractor.loadQuestions(this);
    }

    @Override
    public void onQuestionsLoaded(ArrayList<Question> questions) {
        mQuestionsView.showQuestions(questions);
    }

    @Override
    public void onQuestionsLoadFailed() {
        Snackbar.make(this, "Loading questions failed", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onQuestionClicked(Question question) {
        // show the clicked question's QuestionDetailView
        if (isListViewAttached()) {
            removeViewAt(0);
            View.inflate(getContext(), R.layout.question_detail_layout, this);
        }

        QuestionDetailView detailView = (QuestionDetailView) getChildAt(0);
        detailView.setQuestionItem(question);
    }

    public boolean onBackPressed() {
        if (!isListViewAttached()) {
            removeViewAt(0);
            addView((View) mQuestionsView);
            return true;
        }
        return false;
    }

    private boolean isListViewAttached() {
        return ((QuestionsViewImpl) mQuestionsView).getParent() != null;
    }
}
