package com.raqun.contentparser.content.domain;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.raqun.contentparser.api.Call;
import com.raqun.contentparser.api.Constants;
import com.raqun.contentparser.UseCase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tyln on 11.10.2016.
 */

public class FindWordsAndCalculateFrequency extends UseCase<String> {
    private final String TAG = getClass().getSimpleName();

    @NonNull
    private String mUrl;

    public FindWordsAndCalculateFrequency(@NonNull String url, @NonNull UseCaseCallback callback) {
        super(callback);
        this.mUrl = url;
    }

    @WorkerThread
    @Override
    protected void execute() {
        try {
            final Call call = new Call(mUrl);
            final String content = call.getContent();
            if (!TextUtils.isEmpty(content)) {
                final String convertedContent = content.replaceAll("[\r\n\t]+", " ");
                final String[] words = convertedContent.split("\\s+");
                final HashMap<String, Integer> data = new LinkedHashMap<>();
                String lowerCaseWord;

                for (String word : words) {
                    lowerCaseWord = word.toLowerCase();
                    if (data.containsKey(lowerCaseWord)) {
                        data.put(lowerCaseWord, data.get(lowerCaseWord) + 1);
                    } else {
                        data.put(lowerCaseWord, 1);
                    }
                }

                final StringBuilder stringBuilder = new StringBuilder();
                final Iterator iterator = data.entrySet().iterator();
                Map.Entry keyValuePair;
                while (iterator.hasNext()) {
                    keyValuePair = (Map.Entry) iterator.next();
                    stringBuilder.append(keyValuePair.getKey());
                    stringBuilder.append("(");
                    stringBuilder.append(keyValuePair.getValue());
                    stringBuilder.append(")\n");
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
