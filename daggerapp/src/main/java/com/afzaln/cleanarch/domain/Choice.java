package com.afzaln.cleanarch.domain;

import com.afzaln.cleanarch.data.QuestionDatabase;
import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

/**
 * Created by afzal on 2015-12-07.
 */
@Table(database = QuestionDatabase.class)
@JsonObject(fieldDetectionPolicy = FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class Choice extends BaseModel {

    @PrimaryKey
    public int id = -1;

    @Column
    public String choice;
    @Column
    public String url;
    @Column
    public int votes;

    @JsonIgnore
    @ForeignKey(saveForeignKeyModel = false)
    public ForeignKeyContainer<Question> mQuestionForeignKeyContainer;

    public void associateQuestion(Question question) {
        mQuestionForeignKeyContainer = FlowManager.getContainerAdapter(Question.class).toForeignKeyContainer(question);
    }

    @OnJsonParseComplete
    void onParseComplete() {
        String[] choiceUrl = url.split("/");
        id = Integer.parseInt(choiceUrl[choiceUrl.length - 1]);
    }
}
