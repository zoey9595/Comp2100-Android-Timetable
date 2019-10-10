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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class User {
    private static User userInstance = null;
    private static Course courseInstance;
    private static Compatibility compatibilityInstance = null;
    private JSONObject userCourses = new JSONObject();

    private User(Context context) {
        try {
            courseInstance = Course.getCourseInstance(context);
            compatibilityInstance = Compatibility.getCompatibilityInstance(context);

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
    public Map<String,String> isTimeConflict(List<Map<String,String>> timeToEnrollList)
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
                        String conflictMessage= toEnrollLesson + " is conflicted with "+enrolledLesson +"(" +startEnrolled+" - "+endEnrolled + ")";

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

        conflict = isTimeConflict(timeToEnrollList);

        return conflict;
    }


    //enroll course
    public Map<String,String> save(Map<String,List> toEnrollCourse)
    {
        boolean hasError = false;
        Map<String,String> conflict = new HashMap<>();
        Map<String,String> saveStatus = new HashMap<>();
        conflict = isConflict(toEnrollCourse);

        String key ="";
        for (String s : toEnrollCourse.keySet()) {
             key = s;
        }
        conflict =  isCourseConflict(key);
        if(conflict.size()>0)
            hasError = true;

        if(hasError)
        {
            saveStatus.put(Utility.STATUS,"false");
            saveStatus.put(Utility.MESSAGE,conflict.get("message"));
        }
        else
        {
            saveStatus.put(Utility.STATUS,"true");
            saveStatus.put(Utility.MESSAGE,"Save Successful!");
        }

        return saveStatus;
    }

    public Map<String,String> isCourseConflict(String courseKey)
    {
        Map<String,String> conflict = new HashMap<>();

        try
        {
            String courseId = courseKey.replaceAll("_S1","").replaceAll("_S2","");
            Map<String,String> compatibility = compatibilityInstance.getCoursesCompatiblityById(courseId);
            String requisite = compatibility.get(Utility.REQUISITE);
            String incompatibility = compatibility.get(Utility.INCOMPATIBILITY);

            List<String> requisiteCourses = new ArrayList<>();
            List<String> incompatibilityCourses = new ArrayList<>();

            requisiteCourses = getCompatibilityList(requisite);
            incompatibilityCourses = getCompatibilityList(incompatibility);



            //Check if incompatibility courses have been enrolled
            String enrolledIncompatibleCourse="";
            for (int i = 0; i <incompatibilityCourses.size() ; i++) {
                boolean isIncompatibleCourseEnrolled = false;

                String incompatibleCourseKey =incompatibilityCourses.get(i);

                String userCourseKey = incompatibleCourseKey+"_S1";
                isIncompatibleCourseEnrolled = isEnrolledCourse(userCourseKey);

                if(!isIncompatibleCourseEnrolled)
                {
                    userCourseKey = incompatibleCourseKey+"_S2";
                    isIncompatibleCourseEnrolled = isEnrolledCourse(userCourseKey);
                }
                if(isIncompatibleCourseEnrolled)
                    enrolledIncompatibleCourse = incompatibleCourseKey;
                if(i < incompatibilityCourses.size() -1)
                    enrolledIncompatibleCourse+=", ";

                //replace COMP1110 to true or false
                incompatibility= incompatibility.replaceAll(incompatibleCourseKey,String.valueOf(isIncompatibleCourseEnrolled));
            }

            //Check if requisite courses have been enrolled
            String enrolledRequisitedCourse="";
            for (int i = 0; i <requisiteCourses.size() ; i++) {
                boolean isRequisiteEnrolled = false;

                String requisiteCourseKey =requisiteCourses.get(i);

                String userCourseKey = requisiteCourseKey+"_S1";
                isRequisiteEnrolled = isEnrolledCourse(userCourseKey);

                if(!isRequisiteEnrolled)
                {
                    userCourseKey = requisiteCourseKey+"_S2";
                    isRequisiteEnrolled = isEnrolledCourse(userCourseKey);
                }

                if(isRequisiteEnrolled)
                {
                    enrolledRequisitedCourse = requisiteCourseKey;
                    if(i < requisiteCourses.size() -1)
                        enrolledRequisitedCourse+=", ";
                }


                //replace COMP1110 to 'true' or 'false'
                requisite= requisite.replaceAll(requisiteCourseKey,String.valueOf(isRequisiteEnrolled));
            }

            Boolean isRequisited = stringBooleanExpression(requisite);
            Boolean isIncompatibility = stringBooleanExpression(incompatibility);

            //both Requisite and incompatibility are not satisfied
            if(!isRequisited && isIncompatibility)
            {
                String rMessage =compatibility.get(Utility.REQUISITE);
                String iMessage =compatibility.get(Utility.INCOMPATIBILITY);
                String  or = Pattern.quote("||");
                String  and = Pattern.quote("&&");
                rMessage = rMessage.replaceAll(or,"OR").replaceAll(and,"AND");
                iMessage = iMessage.replaceAll(or,"OR").replaceAll(and,"AND");
                conflict.put(Utility.STATUS,"false");
                conflict.put(Utility.MESSAGE,"To enrol in this course you must have completed "+ rMessage
                        +"; and You are not able to enrol in this course if you have completed "+iMessage);
            } else if(!isRequisited)
            {
                String message =compatibility.get(Utility.REQUISITE);
                String  or = Pattern.quote("||");
                String  and = Pattern.quote("&&");
                message = message.replace(or,"OR").replace(and,"AND");
                conflict.put(Utility.STATUS,"false");
                conflict.put(Utility.MESSAGE,"To enrol in this course you must have completed "+message);
                return conflict;
            }else if(isIncompatibility)
            {
                String message =compatibility.get(Utility.INCOMPATIBILITY);
                String  or = Pattern.quote("||");
                String  and = Pattern.quote("&&");
                message = message.replaceAll(or,"OR").replaceAll(and,"AND");
                conflict.put(Utility.STATUS,"false");
                conflict.put(Utility.MESSAGE,"You are not able to enrol in this course if you have completed "+message);
                return conflict;
            }

        } catch (Exception e) {

        }
        return conflict;
    }

    //get list of compatibility "COMP3670||( (COMP1110 || COMP1140 )&&( MATH1014 || MATH1115))"
    public List<String> getCompatibilityList(String compatibility)
    {
        List<String> courseMatches = new ArrayList<>();

        //pattern COMP1110, COMP1100, COMP6442
        String rexPattern = "\\.*COMP\\d+";
        Pattern pattern = Pattern.compile(rexPattern);
        Matcher match = pattern.matcher(compatibility);

        while (match.find())
            courseMatches.add(match.group());

        return courseMatches;
    }

    //check if the course has been enrolled
    public boolean isEnrolledCourse(String courseKey)
    {
        boolean isEnrolledCourse = false;

        try {
            Iterator<String> courseIds = this.userCourses.keys();
            while (courseIds.hasNext()) {
                String courseId = courseIds.next();

                if(courseId.equals(courseKey))
                    isEnrolledCourse = true;
            }

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }

        return isEnrolledCourse;
    }

    //convert (False||False && True) to Boolean
    public Boolean stringBooleanExpression(String toBoolean)
    {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            Object result = engine.eval(toBoolean);
            return  Boolean.TRUE.equals(result);

        } catch (Exception e) { }

        return false;
    }

}
