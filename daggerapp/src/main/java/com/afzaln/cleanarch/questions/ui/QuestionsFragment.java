package com.afzaln.cleanarch.questions.ui;

import javax.inject.Inject;
import java.util.ArrayList;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.afzaln.cleanarch.QuestionsActivity;
import com.afzaln.cleanarch.R;
import com.afzaln.cleanarch.app.BaseFragment;
import com.afzaln.cleanarch.domain.Question;
import com.afzaln.cleanarch.questions.QuestionsPresenter;
import com.afzaln.cleanarch.questions.QuestionsView;
import icepick.Icepick;
import icepick.State;
import rx.subjects.PublishSubject;

import static com.afzaln.cleanarch.CADaggerApp.getDataComponent;

/**
 * Created by afzal on 2015-12-13.
 */
public class QuestionsFragment extends BaseFragment<QuestionsComponent, QuestionsPresenter> implements QuestionsView {
    public static final String TAG = QuestionsFragment.class.getSimpleName();

    @Inject
    QuestionsPresenter mPresenter;

    @Bind(R.id.questions_list)
    RecyclerView mQuestionsList;

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    PublishSubject<ArrayList<Question>> mDataSubject = PublishSubject.create();
    PublishSubject<Boolean> mLoadingSubject = PublishSubject.create();
    ArrayList<Question> mQuestionsArrayList;

    @State
    boolean mIsLoading;
    private QuestionsAdapter mQuestionsAdapter;

    private RecyclerViewClickListener mItemClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    protected QuestionsComponent onCreateNonConfigurationComponent() {
        // Return the component that will live until this fragment is destroyed by the user
        // (i.e. this component instance will survive configuration changes). Can be later
        // retrieved using getComponent()
        return DaggerQuestionsComponent.builder()
                .dataComponent(getDataComponent(getActivity()))
                .build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQuestionsArrayList = new ArrayList<>();

        // Inject dependencies. Note that we could just use getPresenter() from the base
        // class to get the presenter, but this demonstrates that injecting it works too.
        getComponent().inject(this);

        // Restore all @State annotated members
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save state of all @State annotated members
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        getToolbar().setTitle(R.string.app_name);
        getToolbar().getMenu().clear();
        toggleToolbarProgress(false);

        setupQuestionsListLayout();

        mLoadingSubject.subscribe(loading -> {
            mIsLoading = loading;
            mRefreshLayout.post(() -> mRefreshLayout.setRefreshing(mIsLoading));
//            mQuestionsList.setVisibility(mIsLoading ? View.GONE : View.VISIBLE);
        });

        mDataSubject.subscribe(questions -> {
            mQuestionsArrayList = questions;
            mQuestionsAdapter.setQuestions(questions);
        });

        mLoadingSubject.onNext(mIsLoading);

        mDataSubject.onNext(mQuestionsArrayList);

//        if (savedInstanceState == null) {
            // do things on a new instance of presenter
//            mPresenter.bindView(this);
            mPresenter.loadQuestions();
//        }
    }

    private void setupQuestionsListLayout() {
        mRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.loadQuestions(true);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mQuestionsList.setLayoutManager(layoutManager);

        mItemClickListener = new RecyclerViewClickListener() {
            @Override
            public void onItemHolderClick(int position) {
                onQuestionClicked(position);
            }
        };

        mQuestionsAdapter = new QuestionsAdapter(this, mItemClickListener);
        mQuestionsList.setAdapter(mQuestionsAdapter);
        mQuestionsAdapter.setQuestions(mQuestionsArrayList);
    }

    @Override
    public void showQuestions(ArrayList<Question> questions) {
        mDataSubject.onNext(questions);
    }

    @Override
    public void onQuestionClicked(int position) {
        // start vote activity/fragment
        ((QuestionsActivity) getActivity()).showVoteFragment(mQuestionsArrayList.get(position).id);
    }

    @Override
    public void showLoading() {
        mLoadingSubject.onNext(true);
    }

    @Override
    public void hideLoading() {
        mLoadingSubject.onNext(false);
    }

    @Override
    public void showLoadingError() {
        Snackbar.make(getView(), "Couldn't load questions", Snackbar.LENGTH_SHORT).show();
    }

    public static QuestionsFragment newInstance() {
        QuestionsFragment fragment = new QuestionsFragment();
//        fragment.setAllowEnterTransitionOverlap(true);
//        fragment.setAllowReturnTransitionOverlap(true);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            TransitionSet set = new TransitionSet();
            set.addTransition(new Slide(Gravity.BOTTOM));
            set.addTransition(new Fade(Fade.IN));

            fragment.setEnterTransition(set);
            fragment.setReenterTransition(set);
            fragment.setExitTransition(new Fade(Fade.OUT));
        }
        return fragment;
    }
}
