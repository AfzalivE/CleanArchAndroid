package com.afzaln.cleanarch.questions;

import javax.inject.Inject;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.afzaln.cleanarch.MainActivity;
import com.afzaln.cleanarch.R;
import com.afzaln.cleanarch.app.BaseFragment;
import com.afzaln.cleanarch.app.DaggerQuestionsComponent;
import com.afzaln.cleanarch.models.Question;
import icepick.Icepick;
import icepick.State;
import rx.subjects.PublishSubject;

import static com.afzaln.cleanarch.CADaggerApp.getRepoComponent;

/**
 * Created by afzal on 2015-12-13.
 */
public class QuestionsFragment extends BaseFragment<QuestionsComponent, QuestionsPresenter> implements QuestionsView {
    public static final String TAG = QuestionsFragment.class.getSimpleName();

    @Inject
    QuestionsPresenter mPresenter;

    @Bind(R.id.questions_list)
    RecyclerView mQuestionsList;

    PublishSubject<ArrayList<Question>> mDataSubject = PublishSubject.create();
    PublishSubject<Boolean> mLoadingSubject = PublishSubject.create();

    @State
    ArrayList<Question> mQuestionsArrayList;

    @State
    boolean mIsLoading;

    private QuestionsAdapter mQuestionsAdapter;

    private RecyclerViewClickListener mItemClickListener;

    @Bind(R.id.progress)
    ProgressBar mProgressBar;

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
                .repoComponent(getRepoComponent(getActivity()))
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
            mProgressBar.setVisibility(mIsLoading ? View.VISIBLE : View.GONE);
            mQuestionsList.setVisibility(mIsLoading ? View.GONE : View.VISIBLE);
        });

        mDataSubject.subscribe(questions -> {
            mQuestionsArrayList = questions;
            mQuestionsAdapter.setQuestions(questions);
        });

        mLoadingSubject.onNext(mIsLoading);

        mDataSubject.onNext(mQuestionsArrayList);

        if (savedInstanceState == null) {
            // do things on a new instance of presenter
//            mPresenter.bindView(this);
            mPresenter.loadQuestions();
        }
    }

    private void setupQuestionsListLayout() {
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
        ((MainActivity) getActivity()).showVoteFragment(mQuestionsArrayList.get(position));
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
        return new QuestionsFragment();
    }
}
