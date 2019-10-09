package com.comp6442.group.timetable;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Course {
    private static Course courseInstance = null;
    private static User userInstance = null;
    private JSONObject courses = new JSONObject();

    private Course(Context context) {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.courses);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();

            String jsonFileLine = bReader.readLine();
            while (jsonFileLine != null) {
                stringBuilder.append(jsonFileLine);
                jsonFileLine = bReader.readLine();
            }

            this.courses = new JSONObject(stringBuilder.toString());;

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
    }

    public static Course getCourseInstance(Context context) {
        if (courseInstance == null)
            courseInstance = new Course(context);
        return courseInstance;
    }

    public List<String> getCourseList() {
        Iterator iterator = this.courses.keys();
        List<String> courseList = new ArrayList<>();
        while (iterator.hasNext())
            courseList.add((String) iterator.next());
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

    //added on 1 Oct 2019
    public List<String> getLecDetails(String courseKey) { //get lecture details: LecA/01, Mon, 13:00 - 15:00
        List<String> lectureList = new ArrayList<>();
        try {
            List<Map<String, String>> lessonList = new ArrayList<>();
            lessonList= getLessons(courseKey);
            for (int i = 0; i < lessonList.size(); i++) {
                Map<String, String> lessonInfo = new HashMap<>();
                lessonInfo = reformatLessonInfo(lessonList.get(i));
                if(lessonInfo.get(Utility.NAME_TYPE).equals(Utility.LEC))
                {
                    String combine = lessonInfo.get(Utility.NAME_TYPE)+lessonInfo.get(Utility.NAME_ALP)+'/'+lessonInfo.get(Utility.NAME_INDEX)
                            +", "+lessonInfo.get(Utility.WEEKDAY)+", "+lessonInfo.get(Utility.START)+"-"+lessonInfo.get(Utility.END);
                    if(!lectureList.contains(combine))
                        lectureList.add(combine);
                }
            }

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return lectureList;
    }

    public List<String> getTutWorDetails(String courseKey) {  //get tutorial and workshop details: ComA/01, Mon, 13:00 - 15:00
        List<String> lectureList = new ArrayList<>();
        try {
            List<Map<String, String>> lessonList = new ArrayList<>();
            lessonList= getLessons(courseKey);
            for (int i = 0; i < lessonList.size(); i++) {
                Map<String, String> lessonInfo = new HashMap<>();
                lessonInfo = reformatLessonInfo(lessonList.get(i));
                if(!lessonInfo.get(Utility.NAME_TYPE).equals(Utility.LEC))
                {
                    String combine = lessonInfo.get(Utility.NAME_TYPE)+lessonInfo.get(Utility.NAME_ALP)+'/'+lessonInfo.get(Utility.NAME_INDEX)
                            +", "+lessonInfo.get(Utility.WEEKDAY)+", "+lessonInfo.get(Utility.START)+"-"+lessonInfo.get(Utility.END);
                    if(!lectureList.contains(combine))
                        lectureList.add(combine);
                }
            }

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return lectureList;
    }

    //added on 6 Oct 2019
    public List<Map<String, String>> getLecDetailsInsplit(String courseKey) { //get lecture details: LecA/01, Mon, 13:00 - 15:00
        List<Map<String, String>> lectureDetailList = new ArrayList<>();
        try {
            JSONObject courseDetail = (JSONObject) this.courses.get(courseKey);
            List<Map<String, String>> lessonList = new ArrayList<>();
            lessonList= getLessons(courseKey);
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

    public Map<String, String> reformatLessonInfo(Map<String, String> lesson)
    {
        Map<String, String> reformatedLesson = new HashMap<>();
        try{
            String fullName = (String) lesson.get(Utility.FULL_NAME); //COMP1110_S1-ComA/01
            String [] splitName= splitLessonName(fullName);
            String nameAlp = "";
            if(splitName[1].length()>=4)
            {
                nameAlp = splitName[1].substring(3,splitName[1].length());
                splitName[1] = splitName[1].substring(0, splitName[1].length() - 1);
            }
            reformatedLesson.put(Utility.NAME_TYPE, splitName[1]); //Com
            reformatedLesson.put(Utility.NAME_ALP, nameAlp);//A
            reformatedLesson.put(Utility.NAME_INDEX, splitName[2]);//01
            reformatedLesson.put(Utility.WEEKDAY, Utility.WeekdayDisplay((String) lesson.get(Utility.WEEKDAY)));
            reformatedLesson.put(Utility.START, (String) lesson.get(Utility.START));
            reformatedLesson.put(Utility.END, (String) lesson.get(Utility.END));
        }catch (Exception ex)
        {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return reformatedLesson;

    }

    //Added on 8 Oct 2019
    public Map<String, String> getLessonsByCourseIdAndLessonName(String courseKey,String lessonName) {
        Map<String, String> lessonInfo = new HashMap<>();
        try {
            JSONObject courseDetail = (JSONObject) this.courses.get(courseKey);
            JSONArray lessonArray = (JSONArray) courseDetail.get("lessons");
            for (int index = 0; index < lessonArray.length(); index++) {
                JSONObject lesson = (JSONObject) lessonArray.get(index);
                String lessonFullName = (String) lesson.get(Utility.FULL_NAME);

                if(lessonFullName.contains(lessonName))
                {
                    lessonInfo.put(Utility.FULL_NAME, (String) lesson.get(Utility.FULL_NAME));
                    lessonInfo.put(Utility.START, (String) lesson.get(Utility.START));
                    lessonInfo.put(Utility.END, (String) lesson.get(Utility.END));
                }
            }
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return lessonInfo;
    }

    //get course list that have not enrolled yet // get context from EnroActivity : this
    public List<String> getUnEnrolledCourseList(Context context) {
        Iterator iterator = this.courses.keys();
        List<String> courseList = new ArrayList<>();

        //get all enrolled courses
        userInstance = User.getUserInstance(context);
        Map<String, List<String>> enrolledCourse = userInstance.getUserCourses();
        while (iterator.hasNext())
        {
            boolean isEnrolled = false;
            String courseId = (String) iterator.next();
            //remove the courses that enrolled already
            for (String s : enrolledCourse.keySet()) {
                String enrolledCourseId = s;
                if(s.equals(courseId))
                {
                    isEnrolled =true;
                    break;
                }
            }
            if(!isEnrolled)
                courseList.add(courseId);
        }
        Collections.sort(courseList);
        return courseList;
    }

    //get master data of weekdays and nameType
    public Map<String,List> getMasterList()
    {
        Map<String,List> master = new HashMap<>();
        List<String> weekdays = new ArrayList<>();
        List<String> lessonType = new ArrayList<>();
        weekdays = Utility.getWeekdayList();
        lessonType = Utility.getLessonTypeList();
        master.put(Utility.WEEKDAY,weekdays);
        master.put(Utility.NAME_TYPE,lessonType);
        return master;
    }

    //get split lesson name
    public static String[]  splitLessonName(String Name)
    {
        String[] names = new String[4];
        names = Name.split("-|\\/+");
        return names;
    }

}
