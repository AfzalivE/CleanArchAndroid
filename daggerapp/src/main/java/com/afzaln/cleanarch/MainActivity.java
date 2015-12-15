package com.afzaln.cleanarch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import com.afzaln.cleanarch.fragments.QuestionsFragment;

public class MainActivity extends AppCompatActivity {

    private static final String FLAG_COMMIT_FRAGMENT = "commitFragment";
    private QuestionsFragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        CADaggerApp.getAppComponent(this).inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        boolean commitFragment = intent.getBooleanExtra(FLAG_COMMIT_FRAGMENT, true);

        if (savedInstanceState == null && commitFragment) {
            mFragment = QuestionsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.root, mFragment, QuestionsFragment.TAG)
                    .commit();
        }
    }
}
