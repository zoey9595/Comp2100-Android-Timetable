/**
 * The Course class is a subclass of FileOperator, which implements
 * methods for operating the courses.json file. This file is migrated
 * to the internal storage if it has not been done yet when initialising
 * this class. After that, all operation about the file is the copy that
 * locates at the internal storage. The class contains lots of useful
 * methods that get or set different part of the courses data structure.
 *
 * @author  Yongchao Lyu (u6874539), Jingwei Wang (u6891978)
 * @version 1.0
 * @since   2019-09-20
 */

package com.comp6442.group.timetable;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Course extends FileOperator {
    private static Course courseInstance = null;
    private static User userInstance = null;
    private JSONObject courses = new JSONObject();

    /**
     * @author Yongchao Lyu (u6874539)
     *
     * The constructor of the class for initialising
     * 1. Set the filename of the file to be operated
     * 2. Migrate the resource file to internal storage if it has not been done yet.
     * 3. Read the content of this file from internal storage to a member variable
     *
     * Note: the modifier is "private", which means we are defining a singleton class
     *
     * @param context context of the application
     *
     */
    private Course(Context context) {
        super(context, "courses.json");

        try {
            this.placeInternalFile(R.raw.courses);
            String jsonString = this.readInternalFile();
            if (jsonString != null)
                this.courses = new JSONObject(jsonString);

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
    }

    /**
     * @author Yongchao Lyu (u6874539)
     *
     * Method for getting the instance of this singleton class
     *
     * Note: The class can only be instantiated once for preventing the file
     * to be read several times. This kind of design fobidden read the same file
     * more than once such that the limited memory of the device can be saved.
     *
     * @param context context of the application
     *
     */
    public static Course getCourseInstance(Context context) {
        if (courseInstance == null)
            courseInstance = new Course(context);
        return courseInstance;
    }

    public List<String> getCourseNameList() {
        Iterator iterator = this.courses.keys();
        List<String> courseList = new ArrayList<>();
        while (iterator.hasNext())
            courseList.add((String) iterator.next());
        Collections.sort(courseList);
        return courseList;
    }

    public List<String> getCompCourseNameList() {
        List<String> courseList = new ArrayList<>();

        Iterator iterator = this.courses.keys();
        while (iterator.hasNext()){
            String courseName = (String) iterator.next();
            if (courseName.contains("COMP"))
                courseList.add(courseName);
        }

        Collections.sort(courseList);
        return courseList;
    }

    public String getCourseId(String courseKey) {
        String courseId = "";
        try {
            JSONObject courseDetail = (JSONObject) this.courses.get(courseKey);
            courseId = (String) courseDetail.get("id");
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return courseId;
    }

    public String getCourseName(String courseKey) {
        String courseName = "";
        try {
            JSONObject courseDetail = (JSONObject) this.courses.get(courseKey);
            courseName = (String) courseDetail.get("name");

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return courseName;
    }

    public String getCourseSemester(String courseKey) {
        String semester = "";
        try {
            JSONObject courseDetail = (JSONObject) this.courses.get(courseKey);
            semester = (String) courseDetail.get("semester");

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return semester;
    }

    public List<Map<String, String>> getLessons(String courseKey) {
        List<Map<String, String>> lessonList = new ArrayList<>();
        try {
            JSONObject courseDetail = (JSONObject) this.courses.get(courseKey);
            JSONArray lessonArray = (JSONArray) courseDetail.get("lessons");
            for (int index = 0; index < lessonArray.length(); index++) {
                JSONObject lesson = (JSONObject) lessonArray.get(index);
                Map<String, String> lessonInfo = new HashMap<>();
                Iterator keys = lesson.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    lessonInfo.put(key, (String) lesson.get(key));
                }
                lessonList.add(lessonInfo);
            }

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return lessonList;
    }



    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to retrieve all the lecture details by CourseKey
     *
     *
     */
    //added on 1 Oct 2019
    public List<String> getLecDetails(String courseKey) { //get lecture details: LecA/01, Mon, 13:00 - 15:00
        List<String> lectureList = new ArrayList<>();
        try {
            List<Map<String, String>> lessonList = new ArrayList<>();
            lessonList = getLessons(courseKey);
            for (int i = 0; i < lessonList.size(); i++) {
                Map<String, String> lessonInfo = new HashMap<>();
                lessonInfo = reformatLessonInfo(lessonList.get(i));
                if (lessonInfo.get(Utility.NAME_TYPE).equals(Utility.LEC)) {
                    String combine = lessonInfo.get(Utility.NAME_TYPE) + lessonInfo.get(Utility.NAME_ALP) + '/' + lessonInfo.get(Utility.NAME_INDEX)
                            + ", " + lessonInfo.get(Utility.WEEKDAY) + ", " + lessonInfo.get(Utility.START) + "-" + lessonInfo.get(Utility.END);
                    if (!lectureList.contains(combine))
                        lectureList.add(combine);
                }
            }

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return lectureList;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to retrieve all the lesson details except lecture by CourseKey
     *
     */
    public List<String> getTutWorDetails(String courseKey) {  //get tutorial and workshop details: ComA/01, Mon, 13:00 - 15:00
        List<String> lectureList = new ArrayList<>();
        try {
            List<Map<String, String>> lessonList = new ArrayList<>();
            lessonList = getLessons(courseKey);
            for (int i = 0; i < lessonList.size(); i++) {
                Map<String, String> lessonInfo = new HashMap<>();
                lessonInfo = reformatLessonInfo(lessonList.get(i));
                if (!lessonInfo.get(Utility.NAME_TYPE).equals(Utility.LEC)) {
                    String combine = lessonInfo.get(Utility.NAME_TYPE) + lessonInfo.get(Utility.NAME_ALP) + '/' + lessonInfo.get(Utility.NAME_INDEX)
                            + ", " + lessonInfo.get(Utility.WEEKDAY) + ", " + lessonInfo.get(Utility.START) + "-" + lessonInfo.get(Utility.END);
                    if (!lectureList.contains(combine))
                        lectureList.add(combine);
                }
            }

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return lectureList;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to retrieve all the lecture details in split by CourseKey
     *
     */
    //added on 6 Oct 2019
    public List<Map<String, String>> getLecDetailsInsplit(String courseKey) { //get lecture details: LecA/01, Mon, 13:00 - 15:00
        List<Map<String, String>> lectureDetailList = new ArrayList<>();
        try {
            JSONObject courseDetail = (JSONObject) this.courses.get(courseKey);
            List<Map<String, String>> lessonList = new ArrayList<>();
            lessonList = getLessons(courseKey);
            for (int i = 0; i < lessonList.size(); i++) {
                Map<String, String> lessonInfo = new HashMap<>();
                lessonInfo = reformatLessonInfo(lessonList.get(i));
                lessonInfo.put(Utility.COURSE_NAME, (String) courseDetail.get(Utility.FULL_NAME));
                lectureDetailList.add(lessonInfo);
            }

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return lectureDetailList;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to reformat lesson information
     *
     */
    //splict the lessonName from COMP1110_S1-ComA/01 into  COMP1110_S1, Com, A, 01
    public Map<String, String> reformatLessonInfo(Map<String, String> lesson) {
        Map<String, String> reformatedLesson = new HashMap<>();
        try {
            String fullName = (String) lesson.get(Utility.FULL_NAME); //COMP1110_S1-ComA/01
            String[] splitName = splitLessonName(fullName);
            String nameAlp = "";
            if (splitName[1].length() >= 4) {
                nameAlp = splitName[1].substring(splitName[1].length() - 1, splitName[1].length());
                splitName[1] = splitName[1].substring(0, splitName[1].length() - 1);
            }
            reformatedLesson.put(Utility.NAME_TYPE, splitName[1]); //Com
            reformatedLesson.put(Utility.NAME_ALP, nameAlp);//A
            reformatedLesson.put(Utility.NAME_INDEX, splitName[2]);//01
            reformatedLesson.put(Utility.WEEKDAY, Utility.WeekdayDisplay((String) lesson.get(Utility.WEEKDAY)));
            reformatedLesson.put(Utility.START, (String) lesson.get(Utility.START));
            reformatedLesson.put(Utility.END, (String) lesson.get(Utility.END));
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return reformatedLesson;

    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to retrieve specific lesson by courseKey and LessonName
     *
     */
    //Added on 8 Oct 2019 // get lessons (COMP6490_S2-ComA/01) by courseKey (COMP6490_S2) and lessonName (ComA/01)
    public Map<String, String> getLessonsByCourseIdAndLessonName(String courseKey, String lessonName) {
        Map<String, String> lessonInfo = new HashMap<>();
        try {
            JSONObject courseDetail = (JSONObject) this.courses.get(courseKey);
            JSONArray lessonArray = (JSONArray) courseDetail.get("lessons");
            for (int index = 0; index < lessonArray.length(); index++) {
                JSONObject lesson = (JSONObject) lessonArray.get(index);
                String lessonFullName = (String) lesson.get(Utility.FULL_NAME);

                if (lessonFullName.contains(lessonName)) {
                    lessonInfo.put(Utility.FULL_NAME, (String) lesson.get(Utility.FULL_NAME));
                    lessonInfo.put(Utility.START, (String) lesson.get(Utility.START));
                    lessonInfo.put(Utility.END, (String) lesson.get(Utility.END));
                    lessonInfo.put(Utility.WEEKDAY, (String) lesson.get(Utility.WEEKDAY));
                }
            }
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return lessonInfo;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to retrieve the course list that have not enrolled yet
     *
     * @param context context of the application
     *
     */
    //get course list that have not enrolled yet
    //get context from EnroActivity : this
    public List<String> getUnEnrolledCourseList(Context context) {
        Iterator iterator = this.courses.keys();
        List<String> courseList = new ArrayList<>();

        //get all enrolled courses
        userInstance = User.getUserInstance(context);
        Map<String, List<String>> enrolledCourse = userInstance.getUserCourses();
        List<String> enrolledCourseName = new ArrayList<>();


        for (String s : enrolledCourse.keySet()) {
            enrolledCourseName.add(s);
        }

        while (iterator.hasNext()) {
            boolean isEnrolled = false;
            String courseId = (String) iterator.next();
            if (enrolledCourseName.size() > 0) {
                for (int i = 0; i < enrolledCourseName.size(); i++) {
                    String[] name = splitCourseName(enrolledCourseName.get(i));
                    if (courseId.contains(name[0].toString())) {
                        isEnrolled = true;
                    }
                }
            }
            //if not enroll, list the course in UI
            if (!isEnrolled)
                courseList.add(courseId);

        }
        Collections.sort(courseList);
        return courseList;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to retrieve all kinds of weekdays(Mon-Fri) and nameType(Lec/Com/Dro) of lessons
     *
     */
    //get master data of weekdays and nameType
    public Map<String, List> getMasterList() {
        Map<String, List> master = new HashMap<>();
        List<String> weekdays = new ArrayList<>();
        List<String> lessonType = new ArrayList<>();
        weekdays = Utility.getWeekdayList();
        lessonType = Utility.getLessonTypeList();
        master.put(Utility.WEEKDAY, weekdays);
        master.put(Utility.NAME_TYPE, lessonType);
        return master;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to split the lesson name from COMP1110_S1-LecA/01 into COMP1110_S1, LecA and 01
     *
     */
    //get split lesson name, for example, from COMP6490_S2-ComA/01 to COMP6490_S2, ComA and 01
    public static String[] splitLessonName(String Name) {
        String[] names = new String[4];
        names = Name.split("-|\\/+");
        return names;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to split the course name from COMP1110_S1 into COMP1110 and S1
     *
     */
    //get split lesson name, for example, from COMP6490_S2 to COMP6490 and S2
    public static String[] splitCourseName(String Name) {
        String[] names = new String[4];
        names = Name.split("_");
        return names;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to save course
     *
     */
    public boolean setCourses(String courseKey,JSONObject course) {
        try {

            this.courses.put(courseKey, course);

            // Write to the internal file
            this.writeInternalFile(this.courses.toString());

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
            return false;
        }

        return true;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method using to delete the existing course in Json file.
     *
     */
    public boolean deleteCourse(String courseKey)
    {
        try {
            this.courses.remove(courseKey);

            // Write to the internal file
            this.writeInternalFile(this.courses.toString());

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
            return false;
        }

        return true;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method using to save new course into Json file
     *
     */
    public Map<String, String> save(List<Map<String, String>> course) {
        Map<String, String> saveStatus = new HashMap<>();

        Boolean success = false;
        try {
            JSONObject courseDetails = new JSONObject();
            JSONArray lessonArray = new JSONArray();


            String courseKey ="";
            courseKey =course.get(0).get("id")+"_"+course.get(0).get("semester");//ACST3001_S1

            if(courseKey.equals(""))
                success = false;
            else
            {
                if (course.size() > 0) {

                    courseDetails.put("id", course.get(0).get("id")); //ACST3001
                    courseDetails.put("name", course.get(0).get("courseName"));
                    courseDetails.put("semester", course.get(0).get("semester"));
                }

                for (int i = 0; i < course.size(); i++) {
                    JSONObject lesson = new JSONObject();

                    //add lesson into lesson list
                    lesson.put("name",courseKey+"-"+ course.get(i).get("name"));
                    lesson.put("description", "");//no description of new course
                    lesson.put("weekday", course.get(i).get("weekday"));
                    lesson.put("start", course.get(i).get("start"));
                    lesson.put("end", course.get(i).get("end"));
                    lesson.put("duration", course.get(i).get("duration"));
                    if(course.get(0).get("semester").equals("S1"))
                        lesson.put("weeks", "9-22");//default weeks for S1
                    if(course.get(0).get("semester").equals("S2"))
                        lesson.put("weeks", "30-43");//default weeks for S2
                    lesson.put("location", "");//no location info of new course
                    lessonArray.put(lesson);
                }

                //add lesson list into course object
                courseDetails.put("lessons", lessonArray);
                success = setCourses(courseKey,courseDetails);
            }
            if (success) {
                saveStatus.put(Utility.STATUS, "true");
                saveStatus.put(Utility.MESSAGE, "Save successful!");
            } else {
                saveStatus.put(Utility.STATUS, "false");
                saveStatus.put(Utility.MESSAGE,"Save failed, please try again! ");
            }
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }

        return saveStatus;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method using to delete the existing course.
     *
     */
    public Map<String,String > delete(String courseKey)
    {
        Map<String, String> deleteStatus = new HashMap<>();
        boolean success = false;

        success = deleteCourse(courseKey);
        if (success) {
            deleteStatus.put(Utility.STATUS, "true");
            deleteStatus.put(Utility.MESSAGE, "Delete successful!");
        } else {
            deleteStatus.put(Utility.STATUS, "false");
            deleteStatus.put(Utility.MESSAGE,"Save failed, please try again! ");
        }
        return deleteStatus;
    }

}
