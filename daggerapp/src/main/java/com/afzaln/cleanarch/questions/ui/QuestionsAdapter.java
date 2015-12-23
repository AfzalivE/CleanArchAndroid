package com.afzaln.cleanarch.questions.ui;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.afzaln.cleanarch.R;
import com.afzaln.cleanarch.questions.ui.QuestionsAdapter.QuestionHolder;
import com.afzaln.cleanarch.models.Question;
import com.afzaln.cleanarch.questions.QuestionsView;

/**
 * Created by afzal on 2015-12-07.
 */
public class QuestionsAdapter extends RecyclerView.Adapter<QuestionHolder> {

    private final QuestionsView mQuestionsView;
    private final RecyclerViewClickListener mRecyclerViewClickListener;
    ArrayList<Question> mQuestionsList;

    public QuestionsAdapter(QuestionsView questionsView, RecyclerViewClickListener recyclerViewClickListener) {
        mQuestionsView = questionsView;
        mRecyclerViewClickListener = recyclerViewClickListener;
        mQuestionsList = new ArrayList<>();
    }

    @Override
    public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        QuestionHolder vh = new QuestionHolder(v, mRecyclerViewClickListener);

        return vh;
    }

    @Override
    public void onBindViewHolder(QuestionHolder holder, int position) {
        final Question question = mQuestionsList.get(position);

        holder.setQuestion(question);
    }

    @Override
    public int getItemCount() {
        return mQuestionsList.size();
    }

    public void setQuestions(final ArrayList<Question> questions) {
        mQuestionsList = questions;
        notifyDataSetChanged();
    }

    public static class QuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.question)
        TextView mQuestionView;

        private final RecyclerViewClickListener mClickListener;

        public QuestionHolder(View itemView, RecyclerViewClickListener clickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mClickListener = clickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemHolderClick(getAdapterPosition());
        }

        public void setQuestion(Question question) {
            mQuestionView.setText(question.question);
        }
    }
}
