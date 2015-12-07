package com.afzaln.cleanarch.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.afzaln.cleanarch.R;
import com.afzaln.cleanarch.data.Question;

/**
 * Created by afzal on 2015-12-07.
 */
public class QuestionDetailViewImpl extends LinearLayout implements QuestionDetailView {

    @Bind(R.id.question_title)
    TextView mQuestionView;

    public QuestionDetailViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    @Override
    public void setQuestionItem(Question question) {
        mQuestionView.setText(question.question);
    }
}
