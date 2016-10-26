package com.raqun.contentparser.content;

import android.support.annotation.NonNull;


import com.raqun.contentparser.BasePresenter;
import com.raqun.contentparser.BaseView;

/**
 * Created by tyln on 10.10.2016.
 */

interface SupportContract {
    interface View extends BaseView {
        void showChar(@NonNull String charStr);

        void showChars(@NonNull String charsStr);

        void showWordsAndCounts(@NonNull String wordsAndCountsStr);

        void showIndicator(int indicatorId);

        void hideIndicator(int indicatorId);
    }

    interface Presenter extends BasePresenter<View> {
        void getContent();
    }
}
