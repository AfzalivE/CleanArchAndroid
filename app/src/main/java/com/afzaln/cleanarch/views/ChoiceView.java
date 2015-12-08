package com.afzaln.cleanarch.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.afzaln.cleanarch.R;
import com.afzaln.cleanarch.data.Choice;

/**
 * Created by afzal on 2015-12-07.
 */
public class ChoiceView extends LinearLayout {
    public static final int TOP_PADDING = 8;
    public static final int BOTTOM_PADDING = 8;
    public static final int LEFT_PADDING = 0;
    public static final int RIGHT_PADDING = 16;

    private Choice mChoice;

    @Bind(R.id.choice_checkbox)
    CheckBox mChoiceCheckBox;

    @Bind(R.id.votes)
    TextView mVotesView;

    public ChoiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);


    }

    public void setChoiceItem(Choice choice) {
        mChoiceCheckBox.setText(choice.choice);
        mVotesView.setText(choice.votes + " votes");
    }

    public void setChecked(boolean checked) {
        mChoiceCheckBox.setChecked(checked);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener checkedChangeListener) {
        mChoiceCheckBox.setOnCheckedChangeListener(checkedChangeListener);
    }

    public int getCheckBoxId() {
        return mChoiceCheckBox.getId();
    }
}