package com.afzaln.cleanarch.views;

import com.afzaln.cleanarch.data.Choice;
import com.afzaln.cleanarch.data.Question;

/**
 * Created by afzal on 2015-12-07.
 */
public interface VoteView {
    void setQuestionItem(Question question);
    void updateChoiceItem(Choice choice);
    void setCheckedChoiceUrl(String checkedChoiceUrl);
}
