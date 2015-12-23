package com.afzaln.cleanarch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.afzaln.cleanarch.models.Question;
import com.afzaln.cleanarch.questions.QuestionsFragment;
import com.afzaln.cleanarch.votes.VoteFragment;

public class MainActivity extends AppCompatActivity {

    private static final String FLAG_COMMIT_FRAGMENT = "commitFragment";

    @Bind(R.id.toolbar)
    public Toolbar mToolbar;

    @Bind(R.id.toolbar_progress_bar)
    public ProgressBar mToolbarProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        CADaggerApp.getAppComponent(this).inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

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

    public void showVoteFragment(Question question) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root, VoteFragment.newInstance(question), VoteFragment.TAG)
                .addToBackStack(VoteFragment.TAG)
                .commit();
    }

    public void toggleToolbarProgress(boolean show) {
        mToolbarProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
