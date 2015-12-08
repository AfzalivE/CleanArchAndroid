package com.afzaln.cleanarch.presenters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.afzaln.cleanarch.R;
import com.afzaln.cleanarch.data.Choice;
import com.afzaln.cleanarch.data.Question;
import com.afzaln.cleanarch.interactors.VoteInteractorImpl;
import com.afzaln.cleanarch.views.VoteView;

/**
 * Created by afzal on 2015-12-07.
 */
public class VotePresenterImpl extends LinearLayout implements VotePresenter {

    private final VoteInteractorImpl mVoteInteractor;

    @Bind(R.id.votes_layout)
    VoteView mVoteView;

    public VotePresenterImpl(Context context, AttributeSet attrs) {
        super(context, attrs);

        mVoteInteractor = new VoteInteractorImpl();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    @Override
    public void onVoteSubmit(Question question, String choiceUrl) {
        mVoteInteractor.vote(this, question, choiceUrl);
    }

    @Override
    public void onVoteSuccess(Choice choice) {
        mVoteView.updateChoiceItem(choice);
        Snackbar.make(this, "Vote cast for: " + choice.choice, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onVoteFailure() {
        Snackbar.make(this, "Failed to cast vote", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showQuestionItem(Question question) {
        mVoteInteractor.getQuestion(this, question);
    }

    @Override
    public void onReceivedQuestion(Question question) {
        mVoteView.setQuestionItem(question);
    }

    @Override
    public void onQuestionFailure() {

    }
}
