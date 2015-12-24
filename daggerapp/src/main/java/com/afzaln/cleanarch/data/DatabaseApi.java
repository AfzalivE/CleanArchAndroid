package com.afzaln.cleanarch.data;

import java.util.List;

import com.afzaln.cleanarch.domain.Choice;
import com.afzaln.cleanarch.domain.Choice_Table;
import com.afzaln.cleanarch.domain.Question;
import com.afzaln.cleanarch.domain.Question_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import rx.Observable;

/**
 * Created by afzal on 2015-12-22.
 */
public class DatabaseApi {

    public Observable<List<Question>> getQuestions() {
        List<Question> questions = SQLite.select()
                .from(Question.class)
                .orderBy(Question_Table.id, false)
                .queryList();
        return Observable.just(questions);
    }

    public void saveQuestions(List<Question> questions) {
        for (Question question : questions) {
            question.save();
            for (Choice choice : question.choices) {
                choice.associateQuestion(question);
                choice.save();
            }
        }
    }

    public Observable<Question> getQuestion(int questionId) {
        Question question = SQLite.select()
                .from(Question.class)
                .where(Question_Table.id.eq(questionId))
                .querySingle();

        return Observable.just(question);
    }

    public void saveQuestion(Question question) {
        question.save();
        for (Choice choice : question.choices) {
            choice.associateQuestion(question);
            choice.save();
        }
    }

    public void updateChoice(Choice newChoice) {
        Choice choice = SQLite.select()
                .from(Choice.class)
                .where(Choice_Table.id.eq(newChoice.id))
                .querySingle();

        choice.votes = newChoice.votes;
        choice.update();
    }
}
