/**
 * Author: Yuqing Zhai
 * UID: u6865190
 * <p>
 * This is the guide page introducing how to use this app.
 */
package com.comp6442.group.timetable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        setTitle("Guide");
    }
}