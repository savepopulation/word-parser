package com.raqun.contentparser.content;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.raqun.contentparser.api.Constants;
import com.raqun.contentparser.UseCase;
import com.raqun.contentparser.UseCaseHandler;
import com.raqun.contentparser.content.domain.FindCharAtPosition;
import com.raqun.contentparser.content.domain.FindCharsAtPosition;
import com.raqun.contentparser.content.domain.FindWordsAndCalculateFrequency;


/**
 * Created by tyln on 10.10.2016.
 */

class SupportPresenter implements SupportContract.Presenter {
    private static final int CHAR_POS = 10;

    @Nullable
    private SupportContract.View mView;

    @NonNull
    private final UseCaseHandler mUserCaseHandler;

    @Nullable
    private String mCharString;

    @Nullable
    private String mCharsString;

    @Nullable
    private String mWordsString;

    SupportPresenter() {
        mUserCaseHandler = UseCaseHandler.getInstance();
    }

    @Override
    public void getContent() {
        findChar();
        findChars();
        findWords();
    }

    @Override
    public void subscribe(@NonNull SupportContract.View view) {
        this.mView = view;

        if (!TextUtils.isEmpty(mCharString)) {
            hideIndicator(SupportFragment.PROGRESSBAR_CHAR);
            showChar(mCharString);
        }

        if (!TextUtils.isEmpty(mCharsString)) {
            hideIndicator(SupportFragment.PROGRESSBAR_CHARS);
            showChars(mCharsString);
        }

        if (!TextUtils.isEmpty(mWordsString)) {
            hideIndicator(SupportFragment.PROGRESSBAR_WORDS);
            showWords(mWordsString);
        }
    }

    @Override
    public void unsubscribe() {
        this.mView = null;
    }

    private void findChar() {
        final FindCharAtPosition findCharAtPosition = new FindCharAtPosition(Constants.URL,
                CHAR_POS,
                new UseCase.UseCaseCallback<String>() {
                    @Override
                    public void onSuccess(@NonNull String success) {
                        mCharString = success;
                        hideIndicator(SupportFragment.PROGRESSBAR_CHAR);
                        showChar(success);
                    }

                    @Override
                    public void onError(@NonNull String message) {
                        hideIndicator(SupportFragment.PROGRESSBAR_CHAR);
                        showError(message);
                    }
                });

        showIndicator(SupportFragment.PROGRESSBAR_CHAR);
        mUserCaseHandler.executeUseCase(findCharAtPosition);
    }

    private void findChars() {
        final FindCharsAtPosition findCharsAtPosition = new FindCharsAtPosition(Constants.URL,
                CHAR_POS,
                new UseCase.UseCaseCallback<String>() {
                    @Override
                    public void onSuccess(@NonNull String success) {
                        mCharsString = success;
                        hideIndicator(SupportFragment.PROGRESSBAR_CHARS);
                        showChars(success);
                    }

                    @Override
                    public void onError(@NonNull String error) {
                        hideIndicator(SupportFragment.PROGRESSBAR_CHARS);
                        showError(error);
                    }
                });

        showIndicator(SupportFragment.PROGRESSBAR_CHARS);
        mUserCaseHandler.executeUseCase(findCharsAtPosition);
    }

    private void findWords() {
        final FindWordsAndCalculateFrequency findWordsAndCalculateFrequency = new FindWordsAndCalculateFrequency(Constants.URL,
                new UseCase.UseCaseCallback<String>() {
                    @Override
                    public void onSuccess(@NonNull String success) {
                        mWordsString = success;
                        hideIndicator(SupportFragment.PROGRESSBAR_WORDS);
                        showWords(success);
                    }

                    @Override
                    public void onError(@NonNull String error) {
                        hideIndicator(SupportFragment.PROGRESSBAR_WORDS);
                        showError(error);
                    }
                });
        showIndicator(SupportFragment.PROGRESSBAR_WORDS);
        mUserCaseHandler.executeUseCase(findWordsAndCalculateFrequency);
    }

    private void showChar(@NonNull String charString) {
        if (mView != null) {
            mView.showChar(charString);
        }
    }

    private void showChars(@NonNull String charsString) {
        if (mView != null) {
            mView.showChars(charsString);
        }
    }

    private void showWords(@NonNull String wordsString) {
        if (mView != null) {
            mView.showWordsAndCounts(wordsString);
        }
    }

    private void showError(@Nullable String message) {
        if (mView != null) {
            mView.onDefaultMessage(message);
        }
    }

    private void showIndicator(int indicatorId) {
        if (mView != null) {
            mView.showIndicator(indicatorId);
        }
    }

    private void hideIndicator(int indicatorId) {
        if (mView != null) {
            mView.hideIndicator(indicatorId);
        }
    }
}
