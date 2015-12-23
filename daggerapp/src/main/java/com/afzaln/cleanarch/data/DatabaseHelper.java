package com.afzaln.cleanarch.data;

import java.util.List;

import com.afzaln.cleanarch.models.Choice;
import com.afzaln.cleanarch.models.Question;
import com.afzaln.cleanarch.models.Question_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import rx.Observable;

/**
 * Created by afzal on 2015-12-22.
 */
public class DatabaseHelper {

    public Observable<List<Question>> getQuestions() {
        List<Question> questions = SQLite.select()
                .from(Question.class)
                .queryList();
        return Observable.just(questions);
    }

    public void saveQuestions(List<Question> questions) {
        for (Question question : questions) {
            for (Choice choice : question.choices) {
                choice.save();
            }
            question.save();
        }
    }

    public Observable<Question> getQuestion(int questionId) {
        Question questions = SQLite.select()
                .from(Question.class)
                .where(Question_Table.id.eq(questionId))
                .querySingle();

        return Observable.just(questions);
    }

    public void saveQuestion(Question question) {
        question.save();
    }

    public void saveChoice(Choice choice) {
        choice.save();
    }
}
