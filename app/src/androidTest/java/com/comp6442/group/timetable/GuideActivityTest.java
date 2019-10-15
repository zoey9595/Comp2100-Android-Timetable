/**
 * Author: Yuqing Zhai
 * UID: u6865190
 * <p>
 * This is a UI test for GuideActivity.
 */
package com.comp6442.group.timetable;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class GuideActivityTest {

    @Rule
    public ActivityTestRule<GuideActivity> guideActivityActivityTestRule = new ActivityTestRule<>(GuideActivity.class);

    private GuideActivity guideActivity = null;

    @Before
    public void setUp() throws Exception {
        guideActivity = guideActivityActivityTestRule.getActivity();
    }

    @After
    public void exit() throws Exception {
        guideActivity = null;
    }

    @Test
    public void testLaunch() {
        assertEquals("Guide", guideActivity.getTitle());
    }

}