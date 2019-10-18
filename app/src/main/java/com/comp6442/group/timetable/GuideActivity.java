/**
 * This is the guide page introducing how to use this app.
 *
 * @author Yuqing Zhai (u6865190)
 * @version 1.0
 * @since 2019-10-07
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