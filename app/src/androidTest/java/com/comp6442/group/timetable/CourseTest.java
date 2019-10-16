/**
 * The CourseTest class implements tests for
 * testing the methods defined in Course class
 *
 * @author  Yongchao Lyu (u6874539)
 * @version 1.0
 * @since   2019-10-15
 */

package com.comp6442.group.timetable;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CourseTest {

    private Context appContext;
    private Map<String, List<String>> testCases;

    /**
     * @author  Yongchao Lyu (u6874539)
     *
     * Initial settings and test cases before execute tests
     */
    @Before
    public void initialSetting() {
        appContext = InstrumentationRegistry.getTargetContext();

        testCases = new HashMap<>();
        testCases.put("COMP6710_S1", Arrays.asList("COMP6710", "Structured Programming", "S1"));
        testCases.put("COMP6442_S2", Arrays.asList("COMP6442", "Software Construction", "S2"));
        testCases.put("FINM7003_S1", Arrays.asList("FINM7003", "Continuous Time Finance", "S1"));
        testCases.put("PHIL3076_S2", Arrays.asList("PHIL3076", "Philosophy of the Life Sciences", "S2"));
        testCases.put("VIET6102_S1", Arrays.asList("VIET6102", "Vietnamese 1", "S1"));
    }


    /**
     * @author  Yongchao Lyu (u6874539)
     *
     * Test for the method getCourseInstance
     */
    @Test
    public void getCourseInstance() {
        Course course = Course.getCourseInstance(appContext);
        assertNotNull("\nCannot get an instance of Course.", course);
    }

    /**
     * @author  Yongchao Lyu (u6874539)
     *
     * Test for the method getCourseNameList
     */
    @Test
    public void getCourseNameList() {
        Course course = Course.getCourseInstance(appContext);
        List<String> courseList = course.getCourseNameList();
        assertTrue(
                "\nThe size of the list of course names is abnormal: " + courseList.size(),
                courseList.size() > 2000 && courseList.size() < 10000);
    }

    /**
     * @author  Yongchao Lyu (u6874539)
     *
     * Test for the method getCompCourseNameList
     */
    @Test
    public void getCompCourseNameList() {
        Course course = Course.getCourseInstance(appContext);
        List<String> courseList = course.getCompCourseNameList();
        assertTrue(
                "\nThe size of the list of course names is abnormal: " + courseList.size(),
                courseList.size() > 100 && courseList.size() < 200);
    }

    /**
     * @author  Yongchao Lyu (u6874539)
     *
     * Test for the method getCourseId
     */
    @Test
    public void getCourseId() {
        Course course = Course.getCourseInstance(appContext);
        for (String courseKey: testCases.keySet()) {
            String courseId = course.getCourseId(courseKey);
            String expectId = testCases.get(courseKey).get(0);
            assertEquals(
                    String.format("\nExpect: %s\nGot: %s\n", expectId, courseId),
                    courseId, expectId);
        }
    }

    /**
     * @author  Yongchao Lyu (u6874539)
     *
     * Test for the method getCourseName
     */
    @Test
    public void getCourseName() {
        Course course = Course.getCourseInstance(appContext);
        for (String courseKey: testCases.keySet()) {
            String courseName = course.getCourseName(courseKey);
            String expectName = testCases.get(courseKey).get(1);
            assertEquals(
                    String.format("\nExpect: %s\nGot: %s\n", expectName, courseName),
                    courseName, expectName);
        }
    }

    /**
     * @author  Yongchao Lyu (u6874539)
     *
     * Test for the method getCourseSemester
     */
    @Test
    public void getCourseSemester() {
        Course course = Course.getCourseInstance(appContext);
        for (String courseKey: testCases.keySet()) {
            String courseSemester = course.getCourseSemester(courseKey);
            String expectSemester = testCases.get(courseKey).get(2);
            assertEquals(
                    String.format("\nExpect: %s\nGot: %s\n", expectSemester, courseSemester),
                    courseSemester, expectSemester);
        }
    }

    /**
     * @author  Yongchao Lyu (u6874539)
     *
     * Test for the method getLessons
     */
    @Test
    public void getLessons() {
        Course course = Course.getCourseInstance(appContext);
    }

    /**
     * @author  Yongchao Lyu (u6874539)
     *
     * Test for the method setCourses
     */
    @Test
    public void setCourses() {
    }
}