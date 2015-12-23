package com.afzaln.cleanarch.app;

import android.support.v7.widget.Toolbar;

import com.afzaln.cleanarch.MainActivity;
import nz.bradcampbell.compartment.HasPresenter;
import nz.bradcampbell.compartment.Presenter;
import nz.bradcampbell.compartment.PresenterControllerFragment;

/**
 * Created by afzal on 2015-12-15.
 */
public abstract class BaseFragment<C extends HasPresenter<P>, P extends Presenter> extends PresenterControllerFragment<C, P> {

    Toolbar getToolbar() {
        if (getActivity() != null) {
            return ((MainActivity) getActivity()).mToolbar;
        }

        return null;
    }

    void toggleToolbarProgress(boolean show) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).toggleToolbarProgress(show);
        }
    }
}
