package com.comp6442.group.timetable;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class User {
    private static User userInstance = null;
    private static Course courseInstance;
    private JSONObject userCourses = new JSONObject();

    private User(Context context) {
        try {
            courseInstance = Course.getCourseInstance(context);

            InputStream inputStream = context.getResources().openRawResource(R.raw.user);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();

            String jsonFileLine = bReader.readLine();
            while (jsonFileLine != null) {
                stringBuilder.append(jsonFileLine);
                jsonFileLine = bReader.readLine();
            }

            this.userCourses = new JSONObject(stringBuilder.toString());

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
    }

    public static User getUserInstance(Context context) {
        if (userInstance == null)
            userInstance = new User(context);
        return userInstance;
    }

    public Map<String, List<String>> getUserCourses() {
        Map<String, List<String>> selectedCourses = new HashMap<>();
        try {
            Iterator<String> courseIds = this.userCourses.keys();
            while (courseIds.hasNext()) {
                String courseId = courseIds.next();

                List<String> courseTypes = new ArrayList<>();
                JSONArray course = (JSONArray) this.userCourses.get(courseId);
                for (int index = 0; index < course.length(); index++)
                    courseTypes.add((String) course.get(index));

                selectedCourses.put(courseId, courseTypes);
            }

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }

        return selectedCourses;
    }

    public boolean setUserCourses(Map<String, List> userCourse) {
        try {

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
            return false;
        }

        return true;
    }

    //Added on 8 Oct 2019
    //check if the lectures to be enrolled are conflicted with existing lectures
    public Map<String,String> isConflict(List<Map<String,String>> timeToEnrollList)
    {
        List<Map<String,String>> timeEnrolledList = getLessonsByUser();

        Map<String,String> conflict = new HashMap<>();
        String message="";
        String isConflict = "false";
        for (int i = 0; i < timeToEnrollList.size(); i++) {
            String startToEnroll = timeToEnrollList.get(i).get(Utility.START);
            String endToEnroll = timeToEnrollList.get(i).get(Utility.END);
            String toEnrollLesson = timeToEnrollList.get(i).get(Utility.FULL_NAME);
            String toEnrollWeekday = timeToEnrollList.get(i).get(Utility.WEEKDAY);

            for (int j = 0; j < timeEnrolledList.size(); j++) {
                String startEnrolled = timeEnrolledList.get(j).get(Utility.START);
                String endEnrolled = timeEnrolledList.get(j).get(Utility.END);
                String enrolledLesson = timeEnrolledList.get(j).get(Utility.FULL_NAME);
                String enrolledWeekday = timeEnrolledList.get(j).get(Utility.WEEKDAY);

                if(toEnrollWeekday.equals(enrolledWeekday))
                {
                    //Enrolled : 13:00 - 15:00
                    //To Enroll :  12:00 - 14:00 or 12:00 - 15:00 or 12:00 - 17:00
                    if(Utility.compareTimeInString(startToEnroll,startEnrolled) <0 &&
                            Utility.compareTimeInString(endToEnroll,startEnrolled) >=0)
                        isConflict = "true";

                    //Enrolled : 13:00 - 15:00
                    //To Enroll :  14:00 - 16:00 or 14:30 - 16:00
                    if(Utility.compareTimeInString(startToEnroll,startEnrolled) >0 &&
                            Utility.compareTimeInString(startToEnroll,endEnrolled) < 0)
                        isConflict = "true";

                    //Enrolled : 13:00 - 15:00
                    //To Enroll :  13:00 - ...
                    if(Utility.compareTimeInString(startToEnroll,startEnrolled) ==0)
                        isConflict = "true";

                    //Enrolled : 13:00 - 15:00
                    //To Enroll : ... - 15:00
                    if(Utility.compareTimeInString(endToEnroll,startEnrolled) ==0)
                        isConflict = "true";

                    if(isConflict.equals("true"))
                    {
                        String conflictMessage= toEnrollLesson + "is conflicted"+enrolledLesson +" with " +startEnrolled+"- "+endEnrolled;

                        conflict.put(Utility.STATUS,isConflict);
                        conflict.put(Utility.MESSAGE,conflictMessage);
                        break;
                    }
                }
            }
            if(isConflict.equals("true"))
                break;
        }
        return conflict;
    }

    public List<Map<String, String>> getLessonsByUser()
    {
        List<Map<String, String>> enrolledLessonInfoList = new ArrayList<>();
        Map<String, List<String>> enrolledCourseInfo = getUserCourses();

        //get all lessons info by courseID
        for (String s : enrolledCourseInfo.keySet()) {
            List<String> lessons = enrolledCourseInfo.get(s);
            for (int i = 0; i < lessons.size(); i++) {
                Map<String, String> enrolledLessonInfo = courseInstance.getLessonsByCourseIdAndLessonName(s,lessons.get(i));
                if(enrolledCourseInfo.size()>0)
                    enrolledLessonInfoList.add(enrolledLessonInfo);
            }

        }
        return enrolledLessonInfoList;
    }

    //  get lectures to be enrolled and check conflict
    public Map<String,String> isConflict(Map<String,List> toEnrollCourse)
    {
        Map<String,String> conflict = new HashMap<>();


        List<Map<String,String>> timeToEnrollList = new ArrayList<>();
        //get all lessons info by courseID
        for (String s : toEnrollCourse.keySet()) {
            List<String> lessons = toEnrollCourse.get(s);
            for (int i = 0; i < lessons.size(); i++) {
                Map<String, String> enrolledLessonInfo = courseInstance.getLessonsByCourseIdAndLessonName(s,lessons.get(i));
                if(enrolledLessonInfo.size()>0)
                    timeToEnrollList.add(enrolledLessonInfo);
            }

        }

        conflict = isConflict(timeToEnrollList);

        return conflict;
    }


    //enroll course
    public Map<String,String> save(Map<String,List> toEnrollCourse)
    {
        boolean hasError = false;
        Map<String,String> conflict = new HashMap<>();
        conflict = isConflict(toEnrollCourse);
        if(conflict.size()>0)
            hasError = true;
        else
        {
            conflict.put(Utility.STATUS,"false");
            conflict.put(Utility.MESSAGE,"Save Successful!");
        }

        return conflict;
    }

}
