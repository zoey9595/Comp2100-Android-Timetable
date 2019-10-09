package com.comp6442.group.timetable;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
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

    @Override
    public void setBackgroundColor(int color) {
        StateListDrawable background = new StateListDrawable();
        background.addState(
                new int[]{android.R.attr.state_pressed},
                new ColorDrawable(ContextCompat.getColor(getContext(),R.color.colorSilver)));
        background.addState(
                new int[]{android.R.attr.state_enabled},
                new ColorDrawable(color));
        setBackgroundDrawable(background);
    }
}
