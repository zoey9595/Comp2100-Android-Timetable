package com.comp6442.group.timetable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GuideActivity extends AppCompatActivity {

    private TextView mTvGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        setTitle("Guide");
        mTvGuide = findViewById(R.id.tv_guide);
    }
}
