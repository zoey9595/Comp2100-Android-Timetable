package com.comp6442.group.timetable;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;

public class LessonBlock extends AppCompatTextView {

    public LessonBlock(Context context) {
        super(context);

        setTextSize(12);
        setPadding(2, 0, 2, 0);

        setGravity(Gravity.CENTER);
        setTextColor(ContextCompat.getColor(context, R.color.colorDarken));
    }

}
