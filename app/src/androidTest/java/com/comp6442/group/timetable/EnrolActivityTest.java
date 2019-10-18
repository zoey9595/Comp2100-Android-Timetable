/**
 * This is a UI test for EnrolActivity.
 *
 * @author Yuqing Zhai (u6865190)
 * @version 1.0
 * @since 2019-09-20
 */
package com.comp6442.group.timetable;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnrolActivityTest {

    @Rule
    public ActivityTestRule<EnrolActivity> enrolActivityActivityTestRule = new ActivityTestRule<>(EnrolActivity.class);

    private EnrolActivity enrolActivity = null;

    @Before
    public void setUp() throws Exception {
        enrolActivity = enrolActivityActivityTestRule.getActivity();
    }

    @After
    public void exit() throws Exception {
        enrolActivity = null;
    }

    @Test
    public void testLaunch() {
        View mTvCourseID = enrolActivity.findViewById(R.id.tv_courseID);
        View mTvLectureDetails = enrolActivity.findViewById(R.id.tv_lectureDetails);
        View mTvRecommend = enrolActivity.findViewById(R.id.tv_titleEnrolledCourses);
        View mLvEnrolledCourses = enrolActivity.findViewById(R.id.lv_enrolledCourses);
        View mLvTutorial = enrolActivity.findViewById(R.id.lv_tutorials);
        View mLvRecommend = enrolActivity.findViewById(R.id.lv_recommendCourses);
        View mBtnDelete = enrolActivity.findViewById(R.id.btn_deleteEnroledCourses);
        View mBtnEnrol = enrolActivity.findViewById(R.id.btn_enrol);
        View mBtnAdd = enrolActivity.findViewById(R.id.btn_addnewcourse);
        View mBtnSearchCourse = enrolActivity.findViewById(R.id.btn_course);
        View mBtnSearchTutorial = enrolActivity.findViewById(R.id.btn_tutorial);
        View mLl1 = enrolActivity.findViewById(R.id.ll1);
        View mLl2 = enrolActivity.findViewById(R.id.ll2);
        View mLl3 = enrolActivity.findViewById(R.id.ll3);

        assertNotNull(mTvCourseID);
        assertNotNull(mTvLectureDetails);
        assertNotNull(mTvRecommend);
        assertNotNull(mLvEnrolledCourses);
        assertNotNull(mLvTutorial);
        assertNotNull(mLvRecommend);
        assertNotNull(mBtnDelete);
        assertNotNull(mBtnEnrol);
        assertNotNull(mBtnAdd);
        assertNotNull(mBtnSearchCourse);
        assertNotNull(mBtnSearchTutorial);
        assertNotNull(mLl1);
        assertNotNull(mLl2);
        assertNotNull(mLl3);
        assertEquals("Enrol", enrolActivity.getTitle());
    }

    @Test
    public void checkDeleteButton() throws Throwable {
        final Button mBtnDelete = enrolActivity.findViewById(R.id.btn_deleteEnroledCourses);
        final Button mBtnEnrol = enrolActivity.findViewById(R.id.btn_enrol);
        final Button mBtnAdd = enrolActivity.findViewById(R.id.btn_addnewcourse);
        final Button mBtnSearchCourse = enrolActivity.findViewById(R.id.btn_course);
        final Button mBtnSearchTutorial = enrolActivity.findViewById(R.id.btn_tutorial);
        enrolActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assertTrue(mBtnDelete.performClick());
                assertTrue(mBtnEnrol.performClick());
                assertTrue(mBtnAdd.performClick());
                assertTrue(mBtnSearchCourse.performClick());
                assertTrue(mBtnSearchTutorial.performClick());
            }
        });
    }
}