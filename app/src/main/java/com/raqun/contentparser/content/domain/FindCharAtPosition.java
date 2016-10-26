package com.raqun.contentparser.content.domain;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;


import com.raqun.contentparser.api.Call;
import com.raqun.contentparser.api.Constants;
import com.raqun.contentparser.UseCase;

import java.io.IOException;

/**
 * Created by tyln on 10.10.2016.
 */

public class FindCharAtPosition extends UseCase<String> {
    private final String TAG = FindCharAtPosition.this.getClass().getSimpleName();

    @NonNull
    private String mUrl;

    private final int mPosition;

    public FindCharAtPosition(@NonNull String url, int position, @NonNull UseCaseCallback callback) {
        super(callback);
        this.mUrl = url;
        this.mPosition = position;
    }

    @WorkerThread
    @Override
    protected void execute() {
        try {
            final Call call = new Call(mUrl);
            final String content = call.getContent();
            if (!TextUtils.isEmpty(content)) {
                String response = String.valueOf(content.charAt(mPosition - 1));
                if (TextUtils.isEmpty(response.trim())) {
                    response = Constants.ERROR_EMPTY_CHAR;
                }
                publishSuccess(response);
            } else {
                publishError(Constants.ERROR_CONTENT);
            }
        } catch (IOException | IllegalArgumentException | IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            publishError(Constants.ERROR_UNKNOWN);
        }
    }

    @NonNull
    @Override
    protected String getTag() {
        return TAG;
    }
}
