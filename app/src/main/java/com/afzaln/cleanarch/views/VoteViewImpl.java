package com.afzaln.cleanarch.views;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.afzaln.cleanarch.R;
import com.afzaln.cleanarch.data.Choice;
import com.afzaln.cleanarch.data.Question;
import com.afzaln.cleanarch.presenters.VotePresenter;

/**
 * Created by afzal on 2015-12-07.
 */
public class VoteViewImpl extends LinearLayout implements VoteView {

    @Bind(R.id.question_title)
    TextView mQuestionView;

    @Bind(R.id.choice_list)
    CheckBoxGroup mChoiceList;

    @Bind(R.id.submit_vote)
    Button mSubmitVote;
    private Question mQuestion;

    private String mCheckedChoiceUrl;

    public VoteViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        mSubmitVote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckedChoiceUrl == null) {
                    Snackbar.make(v, "No option selected", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                ((VotePresenter) getParent()).onVoteSubmit(mQuestion, mCheckedChoiceUrl);
            }
        });
    }

    @Override
    public void setQuestionItem(Question question) {
        mQuestion = question;
        mQuestionView.setText(question.question);

        for (int i = 0; i < question.choices.size(); i++) {
            ChoiceView choiceView = (ChoiceView) LayoutInflater.from(getContext()).inflate(R.layout.choice_item, mChoiceList, false);
            choiceView.setChoiceItem(question.choices.get(i));
            mChoiceList.addView(choiceView);
        }

        mChoiceList.onFinishAddingViews();
    }

    @Override
    public void updateChoiceItem(Choice choice) {
        ChoiceView choiceView = mChoiceList.mChoiceMap.get(choice.url);
        choiceView.setChoiceItem(choice);
    }

    @Override
    public void setCheckedChoiceUrl(String checkedChoiceUrl) {
        mCheckedChoiceUrl = checkedChoiceUrl;
    }
}
