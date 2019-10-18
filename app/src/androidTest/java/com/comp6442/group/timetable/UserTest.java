package com.comp6442.group.timetable;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class UserTest {
    private Context appContext;
    private Map<String, List<String>> testCases;

    /**
     * @author  Jingwei Wang (u6891978)
     *
     * Initial settings and test cases before execute tests
     */
    @Before
    public void initialSetting() {
        appContext = InstrumentationRegistry.getTargetContext();

    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method setUserCourses
     */
    @Test
    public void setUserCoursesTest() {
        User userCourse = User.getUserInstance(appContext);
        List<Map<String, String>> lectureDetailList = new ArrayList<>();

        Map<String, String> addStatus = new HashMap<>();
        Map<String, List<String>> newUserCourse = new HashMap<>();
        List<String> lessons = new ArrayList<>();
        lessons.add("LecX/01");
        lessons.add("LecA/01");
        newUserCourse.put("MGMT2030_S1",lessons);
        Boolean setUserCourse = userCourse.setUserCourses(newUserCourse);
        assertTrue(setUserCourse);

    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method isTimeConflict
     */
    @Test
    public void isTimeConflictTest() {
        User userCourse = User.getUserInstance(appContext);
        List<Map<String, String>> timeToEnrollList = new ArrayList<>();
        Map<String, String> isTimeConflict = new HashMap<>();

        Map<String,String> timeToEnrollInfoA = new HashMap<>();
        timeToEnrollInfoA.put(Utility.START,"13:00");
        timeToEnrollInfoA.put(Utility.END,"14:00");
        timeToEnrollInfoA.put(Utility.FULL_NAME,"COMP1100_S1-ComA/01");
        timeToEnrollInfoA.put(Utility.WEEKDAY,"Friday");

        Map<String,String> timeToEnrollInfoB = new HashMap<>();
        timeToEnrollInfoB.put(Utility.START,"11:00");
        timeToEnrollInfoB.put(Utility.END,"14:00");
        timeToEnrollInfoB.put(Utility.FULL_NAME,"CLAS6001_S1-LecC/01");
        timeToEnrollInfoB.put(Utility.WEEKDAY,"Wednesday");

        timeToEnrollList.add(timeToEnrollInfoA);
        timeToEnrollList.add(timeToEnrollInfoB);

        isTimeConflict = userCourse.isTimeConflict(timeToEnrollList);
        assertNotNull(isTimeConflict);

    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method isConflict
     */
    @Test
    public void isConflictTest() {
        User userCourse = User.getUserInstance(appContext);
        Map<String, List<String>> toEnrollCourse = new HashMap<>();
        Map<String, String> isConflict = new HashMap<>();

        List<String> lessons = new ArrayList<>();
        lessons.add("ComA/01");
        lessons.add("ComA/02");
        lessons.add("LecA/01");
        toEnrollCourse.put("COMP6710_S2",lessons);

        isConflict = userCourse.isConflict(toEnrollCourse);
        assertNotNull(isConflict);
    }



    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method save
     */
    @Test
    public void saveTest() {
        User userCourse = User.getUserInstance(appContext);
        Map<String, List<String>> toEnrollCourse  = new HashMap<>();
        List<String> lessons = new ArrayList<>();
        Map<String, String> saveStatus = new HashMap<>();

        lessons.add("ComA/01");
        lessons.add("ComA/02");
        lessons.add("LecA/01");
        toEnrollCourse.put("MGMT2007_S2",lessons);

        saveStatus = userCourse.save(toEnrollCourse);
        assertNotNull(saveStatus);

    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method delete
     */
    @Test
    public void deleteTest() {
        User userCourse = User.getUserInstance(appContext);
        List<Map<String, String>> lectureDetailList = new ArrayList<>();

        Map<String, String> deleteStatus_S1 = new HashMap<>();
        Map<String, String> deleteStatus_S2 = new HashMap<>();
        deleteStatus_S1 = userCourse.delete("MGMT2030_S1");
        deleteStatus_S2 = userCourse.delete("MGMT2007_S2");
        Boolean delete_S1 = Boolean.valueOf(deleteStatus_S1.get("status"));
        Boolean delete_S2 = Boolean.valueOf(deleteStatus_S2.get("status"));
        assertTrue(delete_S1);
        assertTrue(delete_S2);
    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method isCourseConflict
     */
    @Test
    public void isCourseConflictTest() {
        User userCourse = User.getUserInstance(appContext);
        Map<String, String> conflict = new HashMap<>();

        conflict = userCourse.isCourseConflict("COMP6470_S1");
        assertEquals(conflict.size(),0); // no conflict

    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method getCompatibilityList
     */
    @Test
    public void getCompatibilityListTest() {
        User userCourse = User.getUserInstance(appContext);
        List<String> comaptibility = new ArrayList<>();

        comaptibility = userCourse.getCompatibilityList("COMP1100");
        assert (comaptibility.size()>0);

    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method stringBooleanExpression
     */
    @Test
    public void stringBooleanExpressionTest() {
        User userCourse = User.getUserInstance(appContext);
        String trueString="( true || ( false && true ))";
        String falseString="( true && false )";

        Boolean expTrue = userCourse.stringBooleanExpression(trueString);
        Boolean expFalse = userCourse.stringBooleanExpression(falseString);
        assertTrue(expTrue);
        assertFalse(expFalse);

    }

}
