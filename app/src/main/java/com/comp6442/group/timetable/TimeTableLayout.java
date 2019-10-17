/**
 * The TimeTableLayout class is a subclass of LinearLayout, which
 * is used to generate timetable layout of the main activity. This
 * class responsible for initialise layout and invoke methods of view
 * modules for displaying the courses info on the timetable.
 *
 * Reference:
 * The pattern of the layout referred the below link
 * https://github.com/yaoandy107/TimetableUI
 *
 * @author  Yongchao Lyu (u6874539)
 * @version 1.0
 * @since   2019-09-15
 */

package com.comp6442.group.timetable;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TimeTableLayout extends LinearLayout {
    // Constant variables
    private static final int TABLE_COL = 8;
    private static final int TABLE_ROW = 16;
    private static final int TIME_START = 7;
    private static final List<String> WEEK_DAYS =
            Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");

    // Class variables
    private static int TITLE_ROW_HEIGHT;
    private static int BODY_ROW_HEIGHT;

    // Instance variables
    private LinearLayout courseContainer;
    private boolean isInitialized = false;
    private OnClickListener onClickListener = null;

    /**
     * @author Yongchao Lyu (u6874539)
     *
     * Public constructor without AttributeSet
     *
     * @param context Context: the context of this application
     *
     */
    public TimeTableLayout(Context context) {
        super(context);
        inflate(context, R.layout.time_table_layout, this);
    }

    /**
     * @author Yongchao Lyu (u6874539)
     *
     * Public constructor with AttributeSet
     *
     * @param context Context: the context of this application
     *
     */
    public TimeTableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.time_table_layout, this);
    }

    /**
     * @author Yongchao Lyu (u6874539)
     *
     * Called from layout when this view should assign a size and position to each of its children.
     *
     * @param changed boolean: This is a new size or position for this view
     * @param left int: Left position, relative to parent
     * @param top int: Top position, relative to parent
     * @param right int: Right position, relative to parent
     * @param bottom int: Bottom position, relative to parent
     *
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (!isInitialized) {
            BODY_ROW_HEIGHT = Math.round((bottom - top) / 9.5f);
            TITLE_ROW_HEIGHT = Math.round(BODY_ROW_HEIGHT * 0.75f);

            initCourseTable();
            showCourse();

            isInitialized = true;
        }
    }

    /**
     * @author Yongchao Lyu (u6874539)
     *
     * Called as the last phase of inflation, after all child views have been added.
     *
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(getClass().getSimpleName(), "onFinishInflate");
        courseContainer = findViewById(R.id.course_container);
    }

    /**
     * @author Yongchao Lyu (u6874539)
     *
     * Set OnClickListener for a lesson block
     *
     * @param onClickListener OnClickListener: the listener for a lesson block
     *
     */
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * @author Yongchao Lyu (u6874539)
     *
     * Initialize the timetable, display the lines of the table,
     * headers of weedays, index of day clocks.
     *
     */
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
                    tableCell.setText(String.format("%d:00", i + TIME_START));

                tableCell.setZ(5.0f);
                tableCell.setLayoutParams(cellParam);
                tableCell.setId(Integer.parseInt(i + String.valueOf(j)));

                tableRow.addView(tableCell);
            }
            courseContainer.addView(tableRow);
        }

        // Set texts of the first row, weekday index of the timetable
        LinearLayout titleLayout = (LinearLayout) courseContainer.getChildAt(0);
        for (int index = 1; index < TABLE_COL; index++) {
            LessonBlock lessonBlock = (LessonBlock) titleLayout.getChildAt(index);
            lessonBlock.setText(WEEK_DAYS.get(index - 1));
        }
    }

    /**
     * @author Yongchao Lyu (u6874539)
     *
     * Clear all existing lesson blocks
     *
     */
    private void resetCourseTable() {
        for (int row = 1; row < TABLE_ROW; row++) {
            for (int col = 1; col < TABLE_COL; col++) {
                LinearLayout tableRow = (LinearLayout) courseContainer.getChildAt(row);
                if (tableRow != null) {
                    LessonBlock tableCell = (LessonBlock) tableRow.getChildAt(col);
                    tableCell.resetLessonBlock();
                }
            }
        }
        requestLayout();
    }

    /**
     * @author Yongchao Lyu (u6874539)
     *
     * Get color array used for rendering difference lesson blocks
     *
     * @return int array, color array for rendering lesson blocks
     */
    private int[] getColorArray(int colorCount) {
        int[] LessonColors = getContext().getResources().getIntArray(R.array.LessonColors);
        List<Integer> defaultColor = new ArrayList<>();
        for (int color : LessonColors)
            defaultColor.add(color);

        int[] colorArray = new int[colorCount];
        for (int i = 0; i < colorCount; i++) {
            int random = (int) (Math.random() * defaultColor.size());
            colorArray[i] = defaultColor.remove(random);
        }

        return colorArray;
    }

    /**
     * @author Yongchao Lyu (u6874539)
     *
     * Render lesson blocks onto the timetable
     *
     */
    public void showCourse() {
        // Clear existing courses
        resetCourseTable();

        int currentWeek = UserCourse.getCurrentWeek();
        UserCourse userCourse = new UserCourse(getContext());
        List<Map<String, String>> activeLessons = userCourse.getActiveLessons(currentWeek);

        int colorIndex = 0;
        int[] colorArray = getColorArray(activeLessons.size());

        for (Map<String, String> lesson: activeLessons) {
            int startTime = Integer.parseInt(lesson.get("start").split(":")[0].trim());
            int duration = Integer.parseInt(lesson.get("duration").split(":")[0].trim());

            int row = startTime - TIME_START;
            int col = WEEK_DAYS.indexOf(lesson.get("weekday").substring(0, 3)) + 1;

            for (int i = 0; i < duration; i++) {
                LinearLayout tableRow = (LinearLayout) courseContainer.getChildAt(row + i);
                if (tableRow != null) {
                    LessonBlock tableCell = (LessonBlock) tableRow.getChildAt(col);
                    tableCell.setVisibility(View.INVISIBLE);
                    tableCell.setTag(lesson);
                    tableCell.setText(lesson.get("name")
                            .replace("_S1", "")
                            .replace("_S2", ""));
                    tableCell.setBackgroundColor(colorArray[colorIndex]);
                    tableCell.setOnClickListener(this.onClickListener);
                    tableCell.setVisibility(View.VISIBLE);
                }
            }
            colorIndex++;
        }
    }
}
