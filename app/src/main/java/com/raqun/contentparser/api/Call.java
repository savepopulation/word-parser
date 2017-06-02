package com.raqun.contentparser.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.raqun.contentparser.util.ValidationUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by tyln on 10.10.2016.
 */

public final class Call {
    @NonNull
    private String mUrl;

    public Call(@NonNull String url) {
        if (ValidationUtil.isNullOrEmpty(url)) {
            throw new IllegalArgumentException("Url cannot be empty or null!");
        }

        this.mUrl = url;
    }

    @WorkerThread
    @NonNull
    public String getContent() throws IOException {
        final HttpClient client = new DefaultHttpClient();
        final HttpGet request = new HttpGet(mUrl);
        final HttpResponse response = client.execute(request);

        final InputStream in = response.getEntity().getContent();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        final StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        in.close();

        return stringBuilder.toString();
    }
}
