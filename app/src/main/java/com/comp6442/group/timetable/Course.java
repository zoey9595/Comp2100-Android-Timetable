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

            this.courses = new JSONObject(stringBuilder.toString());

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
            JSONObject courseDetail = (JSONObject) this.courses.get(courseKey);
            JSONArray lessonArray = (JSONArray) courseDetail.get("lessons");
            for (int index = 0; index < lessonArray.length(); index++) {
                JSONObject lesson = (JSONObject) lessonArray.get(index);
                String lectureName =(String) lesson.get("name");
                Map<String, String> lessonInfo = new HashMap<>();

                if(lectureName.contains("Lec"))
                {
                    Iterator keys = lesson.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        String name = lectureName.substring(lectureName.length()-7);
                        String weekday =Utility.WeekdayDisplay((String) lesson.get("weekday"));
                        String start = (String) lesson.get("start");
                        String end = (String) lesson.get("end");
                        String combine = name+", "+weekday+", "+start+", "+end;
                        lectureList.add(combine);

                    }

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
            JSONObject courseDetail = (JSONObject) this.courses.get(courseKey);
            JSONArray lessonArray = (JSONArray) courseDetail.get("lessons");
            for (int index = 0; index < lessonArray.length(); index++) {
                JSONObject lesson = (JSONObject) lessonArray.get(index);
                String lectureName =(String) lesson.get("name");
                Map<String, String> lessonInfo = new HashMap<>();

                if(lectureName.contains("Tut") || lectureName.contains("Wor"))
                {
                    Iterator keys = lesson.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        String name = lectureName.substring(lectureName.length()-7);
                        String weekday =Utility.WeekdayDisplay((String) lesson.get("weekday"));
                        String start = (String) lesson.get("start");
                        String end = (String) lesson.get("end");
                        String combine = name+", "+weekday+", "+start+", "+end;
                        lectureList.add(combine);

                    }

                }
            }

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return lectureList;
    }
}
