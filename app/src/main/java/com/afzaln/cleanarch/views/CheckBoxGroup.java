package com.afzaln.cleanarch.views;

import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.afzaln.cleanarch.listeners.OnChoiceCheckedListener;

/**
 * Created by afzal on 2015-12-07.
 */
public class CheckBoxGroup extends LinearLayout {

    HashMap<String, ChoiceView> mChoiceMap;
    String mCheckedChoiceId;

    private OnChoiceCheckedListener mChoiceCheckedListener = new OnChoiceCheckedListener() {
        @Override
        public void onChoiceChecked(String choiceUrl, boolean checked) {
            if (checked) {
                ((VoteView) getParent()).setCheckedChoiceUrl(choiceUrl);
            }
        }
    };

    public CheckBoxGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        mChoiceMap = new HashMap<>();
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        ChoiceView choiceChild = (ChoiceView) child;

        mChoiceMap.put(choiceChild.getChoiceUrl(), choiceChild);
    }

    public void onFinishAddingViews() {
        Iterator<ChoiceView> iterator = mChoiceMap.values().iterator();
        while (iterator.hasNext()) {
            ChoiceView next = iterator.next();
            next.setOnChoiceCheckedListener(mChoiceCheckedListener);
        }
    }

    public String getCheckedChoiceId() {
        return mCheckedChoiceId;
    }
}
