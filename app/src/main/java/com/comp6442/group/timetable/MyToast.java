/**
 * This is a custom toast for conflict message.
 *
 * @author Yuqing Zhai (u6865190)
 * @version 1.0
 * @since 2019-09-20
 */
package com.comp6442.group.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyToast {

    private Toast mToast;

    public MyToast(Context context, CharSequence text, int duration) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_custom_toast, null);
        TextView textView = v.findViewById(R.id.tv_toast);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setView(v);
    }

    public static MyToast makeText(Context context, CharSequence text, int duration) {
        return new MyToast(context, text, duration);
    }

    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }

    // Set toast gravity
    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }

}
