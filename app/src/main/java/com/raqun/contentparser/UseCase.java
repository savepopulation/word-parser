package com.raqun.contentparser;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

/**
 * Created by tyln on 10.10.2016.
 */

public abstract class UseCase<T> {
    @NonNull
    private Handler mHandler;

    @NonNull
    private UseCaseCallback<T> mCallback;

    public UseCase(@NonNull UseCaseCallback<T> callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!");
        }

        this.mCallback = callback;
        this.mHandler = new Handler();
    }

    @WorkerThread
    protected abstract void execute();

    @NonNull
    protected abstract String getTag();

    @WorkerThread
    protected void publishSuccess(final T response) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSuccess(response);
            }
        });
    }

    @WorkerThread
    protected void publishError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onError(error);
            }
        });
    }

    public interface UseCaseCallback<R> {
        void onSuccess(R success);

        void onError(@NonNull String error);
    }
}
