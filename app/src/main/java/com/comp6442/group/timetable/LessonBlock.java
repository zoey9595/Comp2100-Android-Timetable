/**
 * The LessonBlock class is a subclass of AppCompatTextView,
 * which is used to render lesson block on the timetable
 *
 * @author  Yongchao Lyu (u6874539)
 * @version 1.0
 * @since   2019-09-18
 */

package com.comp6442.group.timetable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;

public class LessonBlock extends AppCompatTextView {
    /**
     * Public constructor of this class for initialising.
     *
     * @param context Context: the context of this application
     *
     */
    public LessonBlock(Context context) {
        super(context);

        setTextSize(12);
        setPadding(2, 0, 2, 0);

        setGravity(Gravity.CENTER);
        setTextColor(ContextCompat.getColor(context, R.color.colorDarken));
    }

    /**
     * Sets the background color for this view.
     *
     * @param color int: the color of the background
     *
     */
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

    /**
     * Resets the background color for this view
     *
     */
    public void resetLessonBlock() {
        setText(null);
        setTag(null);
        super.setBackgroundColor(Color.TRANSPARENT);
        setOnClickListener(null);
    }
}
