package com.raqun.contentparser.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by tyln on 10.10.2016.
 */

public final class AlertUtil {
    private AlertUtil() {

    }

    public static void alert(@Nullable Context context, @Nullable String message) {
        if (context != null && !TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
