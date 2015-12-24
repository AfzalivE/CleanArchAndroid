package com.afzaln.cleanarch;

import android.content.Intent;
import android.os.Bundle;

import com.afzaln.cleanarch.app.BaseActivity;
import com.afzaln.cleanarch.questions.ui.QuestionsFragment;
import com.afzaln.cleanarch.votes.ui.VoteFragment;

public class QuestionsActivity extends BaseActivity {

    /**
     * Specify layout for BaseActivity to load
     * and Bind Butterknife with
     */
    public QuestionsActivity() {
        super(R.layout.activity_main);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        CADaggerApp.getAppComponent(this).inject(this);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        boolean commitFragment = intent.getBooleanExtra(FLAG_COMMIT_FRAGMENT, true);

        if (savedInstanceState == null && commitFragment) {
            QuestionsFragment fragment = QuestionsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.root, fragment, QuestionsFragment.TAG)
                    .commit();
        }
    }

    public void showVoteFragment(int questionId) {
//        startActivity(new Intent(this, VoteActivity.class).putExtra("questionId", questionId));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root, VoteFragment.newInstance(questionId), VoteFragment.TAG)
                .addToBackStack(VoteFragment.TAG)
                .commit();
    }
}
