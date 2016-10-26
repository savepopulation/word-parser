package com.raqun.contentparser;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.compat.BuildConfig;
import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by tyln on 10.10.2016.
 */

public class UseCaseHandler {
    private static final int POOL_SIZE_DEFAULT = 4;
    private static final int POOL_SIZE_MAX = 10;
    private static final int TIME_OUT = 30;

    @Nullable
    private static UseCaseHandler sInstance;

    @NonNull
    private ThreadPoolExecutor mThreadPoolExecutor;

    private UseCaseHandler() {
        mThreadPoolExecutor = new ThreadPoolExecutor(POOL_SIZE_DEFAULT,
                POOL_SIZE_MAX,
                TIME_OUT,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(POOL_SIZE_DEFAULT));
    }

    @NonNull
    public synchronized static UseCaseHandler getInstance() {
        if (sInstance == null) {
            sInstance = new UseCaseHandler();
        }
        return sInstance;
    }

    @WorkerThread
    public void executeUseCase(@NonNull final UseCase useCase) {
        Log.e("Executing UseCase:", "" + useCase.getTag());
        mThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                useCase.execute();
            }
        });
    }
}
