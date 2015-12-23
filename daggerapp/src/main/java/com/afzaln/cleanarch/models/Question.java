package com.afzaln.cleanarch.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.afzaln.cleanarch.data.QuestionDatabase;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.OneToMany.Method;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by afzal on 2015-12-07.
 */
@ModelContainer
@Table(database = QuestionDatabase.class)
@JsonObject(fieldDetectionPolicy = FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class Question extends BaseModel implements Serializable {

    @PrimaryKey
    public int id = -1;

    @Column
    public String question;
    @Column
    public String published_at;
    @Column
    public String url;

    public ArrayList<Choice> choices;

    @OneToMany(methods = {Method.ALL}, variableName = "choices")
    public List<Choice> getChoices() {
        if (choices == null || choices.isEmpty()) {
            choices = (ArrayList<Choice>) SQLite.select()
                    .from(Choice.class)
                    .where(Choice_Table.mQuestionForeignKeyContainer_id.eq(id))
                    .queryList();
        }

        return choices;
    }

    @OnJsonParseComplete
    void onParseComplete() {
        String[] questionUrl = url.split("/");
        id = Integer.parseInt(questionUrl[questionUrl.length - 1]);
    }
}
