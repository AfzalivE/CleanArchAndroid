package com.afzaln.cleanarch.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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

    @Bind(R.id.choice_list)
    LinearLayout mChoiceList;

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

        for (int i = 0; i < question.choices.size(); i++) {
            ChoiceView choiceView = (ChoiceView) LayoutInflater.from(getContext()).inflate(R.layout.choice_item, mChoiceList, false);
            mChoiceList.addView(choiceView);
            choiceView.setChoiceItem(question.choices.get(i));
        }
    }
}
