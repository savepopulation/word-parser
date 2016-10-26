package com.raqun.contentparser.content.domain;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.raqun.contentparser.api.Call;
import com.raqun.contentparser.api.Constants;
import com.raqun.contentparser.UseCase;

import java.io.IOException;

/**
 * Created by tyln on 11.10.2016.
 */

public class FindCharsAtPosition extends UseCase<String> {
    private final String TAG = getClass().getSimpleName();

    @NonNull
    private String mUrl;

    private int mPosition;

    public FindCharsAtPosition(@NonNull String url, int position, @NonNull UseCaseCallback callback) {
        super(callback);
        this.mUrl = url;
        this.mPosition = position;
    }

    @Override
    protected void execute() {
        try {
            final Call call = new Call(mUrl);
            final String content = call.getContent();
            if (!TextUtils.isEmpty(content)) {
                final StringBuilder stringBuilder = new StringBuilder();
                int count = content.length() / mPosition;
                for (int i = 1; i <= count; i++) {
                    stringBuilder.append(content.charAt(i * mPosition - 1));
                }
                publishSuccess(stringBuilder.toString());
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
