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

import java.util.ArrayList;
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


    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method getLecDetails
     */
    @Test
    public void getLecDetailsTest() {
        Course course = Course.getCourseInstance(appContext);
        List<String> lectureList = new ArrayList<>();
        lectureList = course.getLecDetails("COMP1100_S1");
        for (int i = 0; i < lectureList.size(); i++) {
            assertTrue(
                    "\nThe lesson is not a lecture: " + lectureList.get(i).toString(),
                    lectureList.get(i).contains("Lec"));
        }

    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method getTutWorDetails
     */
    @Test
    public void getTutWorDetailsTest() {
        Course course = Course.getCourseInstance(appContext);
        List<String> lectureList = new ArrayList<>();
        lectureList = course.getTutWorDetails("COMP1100_S1");
        for (int i = 0; i < lectureList.size(); i++) {
            assertTrue(
                    "\nThe lesson is a lecture: " + lectureList.get(i).toString(),
                    !lectureList.get(i).contains("Lec"));
        }

    }


    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method getLecDetailsInsplit
     */
    @Test
    public void getLecDetailsInsplitTest() {
        Course course = Course.getCourseInstance(appContext);
        List<Map<String, String>> lectureDetailList = new ArrayList<>();
        lectureDetailList = course.getLecDetailsInsplit("COMP1110_S1");
        assert (lectureDetailList.size() >0);

    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method reformatLessonInfo
     */
    @Test
    public void reformatLessonInfoTest() {
        Course course = Course.getCourseInstance(appContext);
        Map<String, String> lesson = new HashMap<>();
        Map<String, String> toReformatedLesson = new HashMap<>();
        lesson.put("name", "CMP1110_S1-ComA/01");
        lesson.put("weekday", "Friday");
        lesson.put("start", "13:00");
        lesson.put("end", "14:00");
        toReformatedLesson.put(Utility.NAME_TYPE, "Com"); //Com
        toReformatedLesson.put(Utility.NAME_ALP, "A");//A
        toReformatedLesson.put(Utility.NAME_INDEX, "01");//01
        toReformatedLesson.put(Utility.WEEKDAY, "Fri");
        toReformatedLesson.put(Utility.START, "13:00");
        toReformatedLesson.put(Utility.END, "14:00");


        Map<String, String> reformatedLesson = new HashMap<>();
        reformatedLesson = course.reformatLessonInfo(lesson);
        assertEquals(toReformatedLesson.size(),reformatedLesson.size());
    }


    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method getMasterList
     */
    @Test
    public void getMasterListTest() {
        Course course = Course.getCourseInstance(appContext);
        Map<String, List> master = new HashMap<>();
        master = course.getMasterList();
        assertTrue(
                "\nThe size of the list of master data abnormal: " + master.size(),
                master.size() >0) ;
    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method splitLessonName
     */
    @Test
    public void splitLessonNameTest() {
        Course course = Course.getCourseInstance(appContext);
        String[] names = new String[4];
        String[] toNames = new String[]{"COMP1110_S1","LecA","01"};
        names = course.splitLessonName("COMP1110_S1-LecA/01");
        System.out.printf(names.toString());
        assertArrayEquals(toNames,names); ;
    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method splitCourseName
     */
    @Test
    public void splitCourseNameTest() {
        Course course = Course.getCourseInstance(appContext);
        String[] names = new String[4];
        String[] toNames = new String[]{"COMP1110","S1"};
        names = course.splitCourseName("COMP1110_S1");
        System.out.printf(names.toString());
        assertArrayEquals(toNames,names); ;
    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method deleteCourse
     */
    @Test
    public void deleteCourseTest() {
        Course course = Course.getCourseInstance(appContext);
        Map<String, String> deleteStatus = new HashMap<>();
        deleteStatus = course.delete("CHMD8022_S1");
        String courseKey = course.getCourseId("CHMD8022_S1");
        assertTrue(courseKey.isEmpty());
    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method deleteCourse
     */
    @Test
    public void saveTest() {
        Course course = Course.getCourseInstance(appContext);
        Map<String, String> saveStatus = new HashMap<>();
        List<Map<String, String>> newCourse = new ArrayList<>();
        Map<String, String> lessonA = new HashMap<>();
        lessonA.put("id","COMP8888"); //ACST3001
        lessonA.put("courseName","New Course for Testing");
        lessonA.put("semester", "S2");
        lessonA.put("name","ComA/01");
        lessonA.put("description", "");//no description of new course
        lessonA.put("weekday","Thursday");
        lessonA.put("start", "15:00");
        lessonA.put("end", "19:00");
        lessonA.put("duration", "4:00");
        lessonA.put("weeks", "30-35,38-43");//default weeks for S1
        lessonA.put("location", "");//no location info of new course

        Map<String, String> lessonB = new HashMap<>();
        lessonB.put("id","COMP8888"); //ACST3001
        lessonB.put("courseName","New Course for Testing");
        lessonB.put("semester", "S2");
        lessonB.put("name","ComA/02");
        lessonB.put("description", "");//no description of new course
        lessonB.put("weekday","Friday");
        lessonB.put("start", "15:00");
        lessonB.put("end", "17:00");
        lessonB.put("duration", "2:00");
        lessonB.put("weeks", "30-35,38-43");//default weeks for S1
        lessonB.put("location", "");//no location info of new course

        Map<String, String> lessonC = new HashMap<>();
        lessonC.put("id","COMP8888"); //ACST3001
        lessonC.put("courseName","New Course for Testing");
        lessonC.put("semester", "S2");
        lessonC.put("name","LecA/01");
        lessonC.put("description", "");//no description of new course
        lessonC.put("weekday","Friday");
        lessonC.put("start", "15:00");
        lessonC.put("end", "17:00");
        lessonC.put("duration", "2:00");
        lessonC.put("weeks", "30-35,38-43");//default weeks for S1
        lessonC.put("location", "");//no location info of new course


        newCourse.add(lessonA);
        newCourse.add(lessonB);
        newCourse.add(lessonC);

        saveStatus = course.save(newCourse);
        String courseKey = course.getCourseId("COMP8888_S2");
        assertTrue(!courseKey.isEmpty());
    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method delete
     */
    @Test
    public void deleteTest() {
        Course course = Course.getCourseInstance(appContext);
        Map<String, String> deleteStatus = new HashMap<>();
        deleteStatus = course.delete("WARS1001_S1");
        String courseKey = course.getCourseId("WARS1001_S1");
        assertTrue(courseKey.isEmpty());
    }
}