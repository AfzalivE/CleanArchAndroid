package com.afzaln.cleanarch.votes.ui;

import javax.inject.Inject;
import java.util.HashMap;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.afzaln.cleanarch.R;
import com.afzaln.cleanarch.app.BaseFragment;
import com.afzaln.cleanarch.domain.Choice;
import com.afzaln.cleanarch.domain.Question;
import com.afzaln.cleanarch.votes.VotePresenter;
import com.afzaln.cleanarch.votes.VoteView;
import icepick.Icepick;
import icepick.State;
import rx.subjects.PublishSubject;

import static com.afzaln.cleanarch.CADaggerApp.getDataComponent;

/**
 * Created by afzal on 2015-12-15.
 */
public class VoteFragment extends BaseFragment<VoteComponent, VotePresenter> implements VoteView {

    public static final String TAG = VoteFragment.class.getSimpleName();
    private static final String QUESTION_ID = "question-id";

    @Inject
    VotePresenter mPresenter;

    @Bind(R.id.progress)
    ProgressBar mProgressBar;

    @Bind(R.id.choice_group)
    RadioGroup mChoiceGroup;

    Question mQuestion;

    @State int mSelectedChoiceId = -1;

    HashMap<Integer, Choice> mChoiceHashMap;

    PublishSubject<Question> mQuestionSubject = PublishSubject.create();
    PublishSubject<Boolean> mQuestionLoadingSubject = PublishSubject.create();
    PublishSubject<Boolean> mVoteLoadingSubject = PublishSubject.create();
    PublishSubject<Choice> mVoteSubmittedSubject = PublishSubject.create();

    private Boolean mIsLoading = false;
    private Boolean mIsVoteLoading = false;

    private OnMenuItemClickListener mMenuItemClickListener = item -> {
        switch (item.getItemId()) {
            case R.id.submit_vote:
                if (mChoiceGroup.getCheckedRadioButtonId() != -1) {
                    int choiceId = (int) mChoiceGroup.findViewById(mChoiceGroup.getCheckedRadioButtonId()).getTag();
                    mPresenter.submitVote(mQuestion, mChoiceHashMap.get(choiceId));
                } else {
                    Snackbar.make(getView(), "Please select an option", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    };

    @Override
    protected VoteComponent onCreateNonConfigurationComponent() {
        // Return the component that will live until this fragment is destroyed by the user
        // (i.e. this component instance will survive configuration changes). Can be later
        // retrieved using getComponent()
        return DaggerVoteComponent.builder()
                .dataComponent(getDataComponent(getActivity()))
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vote_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        int questionId = getArguments().getInt(QUESTION_ID);
        mChoiceHashMap = new HashMap<>();

        mQuestionLoadingSubject.subscribe(loading -> {
            mIsLoading = loading;
            mProgressBar.setVisibility(mIsLoading ? View.VISIBLE : View.GONE);
        });

        mQuestionSubject.subscribe(question -> {
            if (question != null) {
                mQuestion = question;
                getToolbar().setTitle(question.question);

                mChoiceHashMap.clear();
                for (Choice choice : question.choices) {
                    mChoiceHashMap.put(choice.id, choice);

                    AppCompatRadioButton radioButton = (AppCompatRadioButton) LayoutInflater.from(getContext()).inflate(R.layout.choice_item_layout, null, false);
                    radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            mSelectedChoiceId = choice.id;
                        }
                    });
                    updateChoice(radioButton, choice);

                    mChoiceGroup.addView(radioButton);

                    TextView textView = new TextView(getContext());
                    updateChoiceVotes(textView, choice);
                    mChoiceGroup.addView(textView);
                }

                setSelectedChoice(mSelectedChoiceId);
            }
        });

        mVoteSubmittedSubject.subscribe(choice -> {
            if (choice != null) {
                int choiceId = choice.id;
                mChoiceHashMap.put(choice.id, choice);

                Snackbar.make(getView(), "Vote submitted", Snackbar.LENGTH_SHORT).show();

                for (int i = 0; i < mChoiceGroup.getChildCount(); i++) {
                    View child = mChoiceGroup.getChildAt(i);
                    if ((int) child.getTag() == choiceId) {
                        if ((child instanceof TextView) && !(child instanceof AppCompatRadioButton)) {
                            updateChoiceVotes((TextView) child, choice);
                        }
                    }
                }
            }
        });

        mVoteLoadingSubject.subscribe(loading -> {
            mIsVoteLoading = loading;
            toggleToolbarProgress(mIsVoteLoading);
            getToolbar().getMenu().findItem(R.id.submit_vote).setVisible(!mIsVoteLoading);
        });


        getToolbar().inflateMenu(R.menu.menu_main);
        getToolbar().setOnMenuItemClickListener(mMenuItemClickListener);

        mQuestionLoadingSubject.onNext(mIsLoading);
        mQuestionSubject.onNext(mQuestion);
        mVoteSubmittedSubject.onNext(mChoiceHashMap.size() > 0 ? mChoiceHashMap.get(mSelectedChoiceId) : null);
        mVoteLoadingSubject.onNext(mIsVoteLoading);

//        if (savedInstanceState == null) {
        mPresenter.loadQuestion(questionId);
//        }
    }

    private void setSelectedChoice(int choiceId) {
        mSelectedChoiceId = choiceId;
        for (int i = 0; i < mChoiceGroup.getChildCount(); i++) {
            View child = mChoiceGroup.getChildAt(i);
            if ((int) child.getTag() == choiceId) {
                if (child instanceof AppCompatRadioButton) {
                    ((AppCompatRadioButton) child).setChecked(true);
                }
            }
        }
    }

    @Override
    public void updateVoteCount(Choice choice) {
        mVoteSubmittedSubject.onNext(choice);
    }

    private void updateChoiceVotes(TextView view, Choice choice) {
        view.setText(choice.votes + " votes");
        view.setTag(choice.id);
    }

    private void updateChoice(AppCompatRadioButton radioButton, Choice choice) {
        radioButton.setText(choice.choice);
        radioButton.setTag(choice.id);
    }

    @Override
    public void showQuestion(Question question) {
        mQuestionSubject.onNext(question);
    }

    @Override
    public void showQuestionLoading() {
        mQuestionLoadingSubject.onNext(true);
    }

    @Override
    public void hideQuestionLoading() {
        mQuestionLoadingSubject.onNext(false);
    }

    @Override
    public void showVoteLoading() {
        mVoteLoadingSubject.onNext(true);
    }

    @Override
    public void hideVoteLoading() {
        mVoteLoadingSubject.onNext(false);
    }

    @Override
    public void showVoteError() {
        Snackbar.make(getView(), "Vote submission error", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showQuestionError() {
        Snackbar.make(getView(), "Couldn't fetch question", Snackbar.LENGTH_SHORT).show();
    }

    public static Fragment newInstance(int questionId) {
        VoteFragment fragment = new VoteFragment();
        fragment.setAllowEnterTransitionOverlap(true);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            TransitionSet enterSet = new TransitionSet();
            enterSet.addTransition(new Slide(Gravity.BOTTOM));
            enterSet.addTransition(new Fade(Fade.IN));
            fragment.setEnterTransition(enterSet);

            TransitionSet exitSet = new TransitionSet();
//            enterSet.addTransition(new Slide(Gravity.RIGHT));
            enterSet.addTransition(new Fade(Fade.OUT));
            fragment.setExitTransition(exitSet);

            fragment.setReturnTransition(exitSet);

        }

        Bundle questionBundle = new Bundle();
        questionBundle.putInt(VoteFragment.QUESTION_ID, questionId);

        fragment.setArguments(questionBundle);

        return fragment;
    }
}
