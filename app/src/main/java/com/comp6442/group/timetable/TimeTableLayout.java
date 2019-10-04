package com.comp6442.group.timetable;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

public class TimeTableLayout extends LinearLayout {
    private static final int TABLE_COL = 8;
    private static final int TABLE_ROW = 16;

    private static int TITLE_ROW_HEIGHT;
    private static int BODY_ROW_HEIGHT;
    private LinearLayout courseContainer;

    private boolean isInitialized = false;

    public TimeTableLayout(Context context) {
        super(context);
        inflate(context, R.layout.time_table_layout, this);
    }

    public TimeTableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.time_table_layout, this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (!isInitialized) {
            BODY_ROW_HEIGHT = Math.round((bottom - top) / 9.5f);
            TITLE_ROW_HEIGHT = Math.round(BODY_ROW_HEIGHT * 0.75f);

            initCourseTable();
            isInitialized = true;
//            if (initializeListener != null) {
//                initializeListener.onTableInitialized();
//            }
//
//            showCourse(studentCourse);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(getClass().getSimpleName(), "onFinishInflate");
        courseContainer = findViewById(R.id.course_container);
    }

    private void initCourseTable() {
        courseContainer.removeAllViews();

        LayoutParams titleRowParam = new LayoutParams(LayoutParams.MATCH_PARENT, TITLE_ROW_HEIGHT);
        LayoutParams bodyRowParam = new LayoutParams(LayoutParams.MATCH_PARENT, BODY_ROW_HEIGHT);
        LayoutParams cellParam = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
        cellParam.setMargins(5, 5, 5, 5);

        for (int i = 0; i < TABLE_ROW; i++) {
            LinearLayout tableRow = new LinearLayout(getContext());

            tableRow.setGravity(Gravity.CENTER);
            tableRow.setOrientation(LinearLayout.HORIZONTAL);
            tableRow.setLayoutParams(i == 0 ? titleRowParam : bodyRowParam);
            tableRow.setBackgroundResource(i % 2 != 0 ? R.color.colorCloud : R.color.colorWhite);

            for (int j = 0; j < TABLE_COL; j++) {
                LessonBlock tableCell = new LessonBlock(getContext());

                // Set texts of the first column, time index of the timetable
                if (j == 0 && i > 0)
                    tableCell.setText(String.format("%d:00", i + 7));

                tableCell.setZ(5.0f);
                tableCell.setLayoutParams(cellParam);
                tableCell.setId(Integer.parseInt(i + String.valueOf(j)));

                tableRow.addView(tableCell);
            }
            courseContainer.addView(tableRow);
        }

        // Set texts of the first row, weekday index of the timetable
        LinearLayout titleLayout = (LinearLayout) courseContainer.getChildAt(0);
        List<String> weekdays = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
        for (int index = 1; index < 8; index++) {
            LessonBlock lessonBlock = (LessonBlock) titleLayout.getChildAt(index);
            lessonBlock.setText(weekdays.get(index - 1));
        }
    }
}
