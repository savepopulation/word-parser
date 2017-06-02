package com.raqun.contentparser.content;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.raqun.contentparser.R;


public final class SupportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout_main, SupportFragment.newInstance())
                    .commit();
        }
    }
}
