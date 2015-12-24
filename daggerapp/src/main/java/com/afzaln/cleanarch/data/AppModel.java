package com.afzaln.cleanarch.data;

import java.util.List;

import com.afzaln.cleanarch.domain.Choice;
import com.afzaln.cleanarch.domain.Question;
import rx.Observable;

/**
 * Created by afzal on 2015-12-13.
 */
public class AppModel {

    private final NetworkApi mNetworkApi;
    private final DatabaseApi mDatabaseApi;

    public AppModel(NetworkApi networkApi, DatabaseApi databaseHelper) {
        mNetworkApi = networkApi;
        mDatabaseApi = databaseHelper;
    }

    public Observable<List<Question>> getQuestions(boolean forceNetworkRefresh) {
        if (forceNetworkRefresh) {
            mDatabaseApi.clear();
            return mNetworkApi.getQuestions();
        } else {
            return Observable.concat(mDatabaseApi.getQuestions(), mNetworkApi.getQuestions())
                    .first(questions -> !questions.isEmpty());
        }
    }

    public Observable<Question> getQuestion(int questionId) {
        return Observable.concat(mDatabaseApi.getQuestion(questionId), mNetworkApi.getQuestion(questionId))
                .first(question -> question != null && question.choices != null && !question.choices.isEmpty());
    }

    public Observable<Choice> submitVote(int questionId, int choiceId) {
        // TODO if offline, store vote to submit later perhaps?
        return mNetworkApi.submitVote(questionId, choiceId);
    }
}
