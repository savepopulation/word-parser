package com.raqun.contentparser.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.raqun.contentparser.R;
import com.raqun.contentparser.util.AlertUtil;

/**
 * Created by tyln on 10.10.2016.
 */

public class SupportFragment extends Fragment implements SupportContract.View, View.OnClickListener {
    static final int PROGRESSBAR_CHAR = 0;
    static final int PROGRESSBAR_CHARS = 1;
    static final int PROGRESSBAR_WORDS = 2;

    @NonNull
    private SupportContract.Presenter mPresenter;

    @NonNull
    private TextView mTextViewChar;

    @NonNull
    private TextView mTextViewChars;

    @NonNull
    private TextView mTextViewWords;

    @NonNull
    private ProgressBar mProgressBarChar;

    @NonNull
    private ProgressBar mProgressBarChars;

    @NonNull
    private ProgressBar mProgressBarWords;

    @NonNull
    public static SupportFragment newInstance() {
        return new SupportFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = new SupportPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_support, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (view == null) return;

        mTextViewChar = (TextView) view.findViewById(R.id.textview_char);
        mTextViewChars = (TextView) view.findViewById(R.id.textview_chars);
        mTextViewWords = (TextView) view.findViewById(R.id.textview_words);

        mProgressBarChar = (ProgressBar) view.findViewById(R.id.progressbar_char);
        mProgressBarChars = (ProgressBar) view.findViewById(R.id.progressbar_chars);
        mProgressBarWords = (ProgressBar) view.findViewById(R.id.progressbar_words);

        view.findViewById(R.id.button_support).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
    }

    @Override
    public void onPause() {
        mPresenter.unsubscribe();
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_support:
                mPresenter.getContent();
                break;
        }
    }

    @Override
    public void showChar(@NonNull String charStr) {
        mTextViewChar.setText(charStr);
    }

    @Override
    public void showChars(@NonNull String charsStr) {
        mTextViewChars.setText(charsStr);
    }

    @Override
    public void showWordsAndCounts(@NonNull String wordsAndCountsStr) {
        mTextViewWords.setText(wordsAndCountsStr);
    }

    @Override
    public void showIndicator(int indicatorId) {
        getIndicator(indicatorId).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideIndicator(int indicatorId) {
        getIndicator(indicatorId).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDefaultMessage(@Nullable String message) {
        AlertUtil.alert(getActivity().getApplicationContext(), message);
    }

    @NonNull
    private ProgressBar getIndicator(int indicatorId) {
        switch (indicatorId) {
            case PROGRESSBAR_CHAR:
                return mProgressBarChar;

            case PROGRESSBAR_CHARS:
                return mProgressBarChars;

            case PROGRESSBAR_WORDS:
                return mProgressBarWords;

            default:
                throw new IllegalArgumentException("No view found for given id!!!");
        }
    }
}
