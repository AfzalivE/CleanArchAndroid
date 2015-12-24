package com.afzaln.cleanarch.app;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.afzaln.cleanarch.R;

/**
 * Created by afzal on 2015-12-23.
 */
public class BaseActivity extends AppCompatActivity {

    protected static final String FLAG_COMMIT_FRAGMENT = "commitFragment";

    @LayoutRes
    int mLayout;

    public BaseActivity(@LayoutRes int layout) {
        super();
        mLayout = layout;
    }

    @Bind(R.id.toolbar)
    public Toolbar mToolbar;

    @Bind(R.id.toolbar_progress_bar)
    public ProgressBar mToolbarProgressBar;

    public void toggleToolbarProgress(boolean show) {
        mToolbarProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(mLayout);
        ButterKnife.bind(this);
    }
}
