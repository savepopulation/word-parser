package com.raqun.contentparser;

import android.support.annotation.NonNull;

/**
 * Created by tyln on 10.10.2016.
 */

public interface BasePresenter<T extends BaseView> {
    void subscribe(@NonNull T view);

    void unsubscribe();
}
