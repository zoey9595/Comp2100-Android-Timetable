/**
 * Author: Xiaochan Zhang
 * UID: u6855326
 * UI test for AddActivity. Inspire by test of EnrolActivity;
 */
package com.comp6442.group.timetable;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AddActivityTest {

    @Rule
    public ActivityTestRule<AddActivity> addActivityActivityTestRule = new ActivityTestRule<>(AddActivity.class);

    private AddActivity addActivity = null;

    @Before
    public void setUp() throws Exception {
        addActivity = addActivityActivityTestRule.getActivity();
    }

    @After
    public void exit() throws Exception {
        addActivity = null;
    }

    @Test
    public void testLaunch() {
        View mEditCID = addActivity.findViewById(R.id.edit_cid);
        View mEditCName = addActivity.findViewById(R.id.edit_cname);
        View mSpSemester = addActivity.findViewById(R.id.spinner_semester);
        View mBtnFind   = addActivity.findViewById(R.id.btn_findCourse);
        View mBtnAdd    = addActivity.findViewById(R.id.btn_addclass);
        View mBtnSave   = addActivity.findViewById(R.id.btn_add_save);
        View mBtnDelete = addActivity.findViewById(R.id.btn_add_delete);
        View mListViewDetail = addActivity.findViewById(R.id.lv_add_detail);
        View mlladd1 = addActivity.findViewById(R.id.ll_add1);
        View mlladd2 = addActivity.findViewById(R.id.ll_add2);
        View mlladd3 = addActivity.findViewById(R.id.ll_add3);

        assertNotNull(mEditCID);
        assertNotNull(mEditCName);
        assertNotNull(mSpSemester);
        assertNotNull(mBtnFind);
        assertNotNull(mBtnAdd);
        assertNotNull(mBtnSave);
        assertNotNull(mBtnDelete);
        assertNotNull(mListViewDetail);
        assertNotNull(mlladd1);
        assertNotNull(mlladd2);
        assertNotNull(mlladd3);
        assertEquals("Add Course", addActivity.getTitle());
    }

    @Test
    public void checkButton() throws Throwable{
        final Button mBtnFind   = addActivity.findViewById(R.id.btn_findCourse);
        final Button mBtnAdd    = addActivity.findViewById(R.id.btn_addclass);
        final Button mBtnSave   = addActivity.findViewById(R.id.btn_add_save);
        final Button mBtnDelete = addActivity.findViewById(R.id.btn_add_delete);
        addActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assertTrue(mBtnFind.performClick());
                assertTrue(mBtnAdd.performClick());
                assertTrue(mBtnSave.performClick());
                assertTrue(mBtnDelete.performClick());
            }
        });
    }
}