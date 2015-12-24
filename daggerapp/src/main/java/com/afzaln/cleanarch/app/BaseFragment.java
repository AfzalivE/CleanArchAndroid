package com.afzaln.cleanarch.app;

import android.support.v7.widget.Toolbar;

import nz.bradcampbell.compartment.HasPresenter;
import nz.bradcampbell.compartment.Presenter;
import nz.bradcampbell.compartment.PresenterControllerFragment;

/**
 * Created by afzal on 2015-12-15.
 */
public abstract class BaseFragment<C extends HasPresenter<P>, P extends Presenter> extends PresenterControllerFragment<C, P> {

    protected Toolbar getToolbar() {
        if (getActivity() != null) {
            return ((BaseActivity) getActivity()).mToolbar;
        }

        return null;
    }

    protected void toggleToolbarProgress(boolean show) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).toggleToolbarProgress(show);
        }
    }
}
