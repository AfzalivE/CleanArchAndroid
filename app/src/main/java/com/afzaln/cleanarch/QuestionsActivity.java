package com.afzaln.cleanarch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.afzaln.cleanarch.presenters.QuestionsPresenter;

public class QuestionsActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.presenter)
    QuestionsPresenter mQuestionsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CleanArchApp.getRefWatcher(this).watch(this);
    }

    @Override public void onBackPressed() {
        boolean handled = mQuestionsPresenter.onBackPressed();
        if (!handled) {
            finish();
        }
    }
}
