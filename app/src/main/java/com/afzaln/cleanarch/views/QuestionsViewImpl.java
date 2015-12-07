package com.afzaln.cleanarch.views;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.afzaln.cleanarch.R;
import com.afzaln.cleanarch.adapters.QuestionsAdapter;
import com.afzaln.cleanarch.adapters.RecyclerViewClickListener;
import com.afzaln.cleanarch.data.Question;
import com.afzaln.cleanarch.presenters.QuestionsPresenterImpl;

/**
 * Created by afzal on 2015-12-07.
 */
public class QuestionsViewImpl extends LinearLayout implements QuestionsView {

    @Bind(R.id.questions_list)
    EmptyRecyclerView mQuestionsList;

    private QuestionsAdapter mQuestionsAdapter;

    private ArrayList<Question> mQuestions;
    private RecyclerViewClickListener mItemClickListener;

    public QuestionsViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        setupQuestionsList();
    }

    private void setupQuestionsList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mQuestionsList.setLayoutManager(layoutManager);

        mItemClickListener = new RecyclerViewClickListener() {
            @Override
            public void onItemHolderClick(int position) {
                onQuestionClicked(position);
            }
        };

        mQuestionsAdapter = new QuestionsAdapter(this, mItemClickListener);
        mQuestionsList.setAdapter(mQuestionsAdapter);
    }

    @Override
    public void showQuestions(ArrayList<Question> questions) {
        mQuestions = questions;
        mQuestionsAdapter.setQuestions(mQuestions);
    }

    @Override
    public void onQuestionClicked(int position) {
        getQuestionPresenter().onQuestionClicked(mQuestions.get(position));
    }

    QuestionsPresenterImpl getQuestionPresenter() {
        return (QuestionsPresenterImpl) getParent();
    }
}
