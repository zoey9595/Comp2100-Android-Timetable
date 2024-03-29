/**
 * The User class is a subclass of FileOperator, which implements
 * methods for operating the user.json file. This file is migrated
 * to the internal storage if it has not been done yet when initialising
 * this class. After that, all operation about the file is the copy that
 * locates at the internal storage. The class contains lots of useful
 * methods that get or set different part of the user data structure.
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class User extends FileOperator{
    private static User userInstance = null;

    private Course courseInstance = null;
    private Compatibility compatibilityInstance = null;
    private JSONObject userCourses = new JSONObject();

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
    private User(Context context) {
        super(context, "user.json");

        try {
            this.courseInstance = Course.getCourseInstance(context);
            this.compatibilityInstance = Compatibility.getCompatibilityInstance(context);

            this.placeInternalFile(R.raw.user);
            String jsonString = this.readInternalFile();
            if (jsonString != null)
                this.userCourses = new JSONObject(jsonString);

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
    public static User getUserInstance(Context context) {
        if (userInstance == null)
            userInstance = new User(context);
        return userInstance;
    }

    /**
     * @author Yongchao Lyu (u6874539)
     *
     * Read user selected courses from inernal file system,
     * and convert the JSON data to java programming data
     *
     * @return Map<String, List<String>>, Key is the courseKey, Value is the list of lessons
     *
     */
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

    /**
     * @author Yongchao Lyu (u6874539)
     *
     * Convert the user courses data to JSON format,
     * and write to internal file system
     *
     * @param userCourse, Map<String, List<String>>
     *                    Key is the courseKey, Value is the list of lessons
     * @return boolean, true if write successful, false otherwise
     *
     */
    public boolean setUserCourses(Map<String, List<String>> userCourse) {
        try {
            // Convert data structure and update to userCourses
            for (String courseID: userCourse.keySet()) {
                JSONArray lessonList = new JSONArray();
                for (String lesson: userCourse.get(courseID)) {
                    lessonList.put(lesson);
                }
                // Add a new course or update a exist course
                this.userCourses.put(courseID, lessonList);
            }
            // Write to the internal storage
            this.writeInternalFile(this.userCourses.toString());

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
            return false;
        }

        return true;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to delete the enrolled course
     *
     *
     */
    //delete user course in user.json file
    public Map<String,String > delete(String courseKey)
    {
        Map<String, String> deleteStatus = new HashMap<>();

        boolean success = false;
        success = deleteUserCourse(courseKey);
        if (success) {
            deleteStatus.put(Utility.STATUS, "true");
            deleteStatus.put(Utility.MESSAGE, "Delete successful!");
        } else {
            deleteStatus.put(Utility.STATUS, "false");
            deleteStatus.put(Utility.MESSAGE,"Save failed, please try again! ");
        }
        return deleteStatus;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to delete enrolled course
     *
     *
     */
    //call the function to delete user course in user.json file
    public boolean deleteUserCourse(String courseKey)
    {
        try {
            this.userCourses.remove(courseKey);

            // Write to the internal file
            this.writeInternalFile(this.userCourses.toString());

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to check if the enrolled course has time conflict with the course to be enrolling.
     *
     *
     */
    //Added on 8 Oct 2019
    //check if the lectures to be enrolled are conflicted with existing lectures
    public Map<String, String> isTimeConflict(List<Map<String, String>> timeToEnrollList) {
        List<Map<String, String>> timeEnrolledList = getLessonsByUser();

        Map<String, String> conflict = new HashMap<>();
        String message = "";
        String isConflict = "false";
        for (int i = 0; i < timeToEnrollList.size(); i++) {//loop the lessons to be enrolled
            String startToEnroll = timeToEnrollList.get(i).get(Utility.START);
            String endToEnroll = timeToEnrollList.get(i).get(Utility.END);
            String toEnrollLesson = timeToEnrollList.get(i).get(Utility.FULL_NAME);
            String toEnrollWeekday = timeToEnrollList.get(i).get(Utility.WEEKDAY);

            for (int j = 0; j < timeEnrolledList.size(); j++) {//loop the lessons that have been enrolled
                String startEnrolled = timeEnrolledList.get(j).get(Utility.START);
                String endEnrolled = timeEnrolledList.get(j).get(Utility.END);
                String enrolledLesson = timeEnrolledList.get(j).get(Utility.FULL_NAME);
                String enrolledWeekday = timeEnrolledList.get(j).get(Utility.WEEKDAY);

                String semesterEnrolled = enrolledLesson.substring(9,11);
                String semesterToEnrolled = toEnrollLesson.substring(9,11);

                //check lessons time conflict on the semester and same day
                if (toEnrollWeekday.equals(enrolledWeekday) && semesterEnrolled.equals(semesterToEnrolled)) {
                    //Enrolled : 13:00 - 15:00
                    //To be Enroll :  12:00 - 14:00 or 12:00 - 15:00 or 12:00 - 17:00
                    if (Utility.compareTimeInString(startToEnroll, startEnrolled) < 0 &&
                            Utility.compareTimeInString(endToEnroll, startEnrolled) >= 0)
                        isConflict = "true";

                    //Enrolled : 13:00 - 15:00
                    //To be Enroll :  14:00 - 16:00 or 14:30 - 16:00
                    if (Utility.compareTimeInString(startToEnroll, startEnrolled) > 0 &&
                            Utility.compareTimeInString(startToEnroll, endEnrolled) < 0)
                        isConflict = "true";

                    //Enrolled : 13:00 - 15:00
                    //To be Enroll :  13:00 - ...
                    if (Utility.compareTimeInString(startToEnroll, startEnrolled) == 0)
                        isConflict = "true";

                    //Enrolled : 13:00 - 15:00
                    //To be Enroll : ... - 15:00
                    if (Utility.compareTimeInString(endToEnroll, startEnrolled) == 0)
                        isConflict = "true";

                    if (isConflict.equals("true")) {
                        String conflictMessage = toEnrollLesson + " is conflicted with " + enrolledLesson + "(" + startEnrolled + " - " + endEnrolled + ") on " + enrolledWeekday;
                        conflict.put(Utility.STATUS, isConflict);
                        conflict.put(Utility.MESSAGE, conflictMessage);
                        break;
                    }
                }
            }
            if (isConflict.equals("true"))
                break;
        }
        return conflict;
    }

    public List<Map<String, String>> getLessonsByUser() {
        List<Map<String, String>> enrolledLessonInfoList = new ArrayList<>();
        Map<String, List<String>> enrolledCourseInfo = getUserCourses();

        //get all lessons info by courseID
        for (String s : enrolledCourseInfo.keySet()) {
            List<String> lessons = enrolledCourseInfo.get(s);
            for (int i = 0; i < lessons.size(); i++) {
                Map<String, String> enrolledLessonInfo = this.courseInstance.getLessonsByCourseIdAndLessonName(s, lessons.get(i));
                if (enrolledCourseInfo.size() > 0)
                    enrolledLessonInfoList.add(enrolledLessonInfo);
            }

        }
        return enrolledLessonInfoList;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to check course conflict
     *
     *
     */
    //  get lectures to be enrolled and check conflict
    public Map<String, String> isConflict(Map<String, List<String>> toEnrollCourse) {
        Map<String, String> conflict = new HashMap<>();

        List<Map<String, String>> timeToEnrollList = new ArrayList<>();
        //get all lessons info by courseID
        for (String s : toEnrollCourse.keySet()) {
            List<String> lessons = toEnrollCourse.get(s);
            for (int i = 0; i < lessons.size(); i++) {
                Map<String, String> enrolledLessonInfo = this.courseInstance.getLessonsByCourseIdAndLessonName(s, lessons.get(i));
                if (enrolledLessonInfo.size() > 0)
                    timeToEnrollList.add(enrolledLessonInfo);
            }
        }

        conflict = isTimeConflict(timeToEnrollList);

        return conflict;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to enroll course
     *
     *
     */
    //enroll course
    public Map<String, String> save(Map<String, List<String>> toEnrollCourse) {
        boolean hasConflict = false;
        Map<String, String> conflict = new HashMap<>();
        Map<String, String> saveStatus = new HashMap<>();
        conflict = isConflict(toEnrollCourse);

        String key = "";
        for (String s : toEnrollCourse.keySet()) {
            key = s;
        }
        if (conflict.size() <= 0)
            conflict = isCourseConflict(key);//check if this course has requisite or incompatibility

        if (conflict.size() > 0)
            hasConflict = true;

        if(hasConflict)
        {
            saveStatus.put(Utility.STATUS,"false");
            saveStatus.put(Utility.MESSAGE,conflict.get("message"));
        }
        else
        {
            boolean success = true;
            try {
                JSONObject enrollUserCourse = new JSONObject();
                //get all lessons info by courseID
                for (String s : toEnrollCourse.keySet()) {
                    List<String> lessons = toEnrollCourse.get(s);
                    if(lessons.size() ==1)
                    {
                        String regex = "(.)*(\\d)(.)*";
                        Pattern pattern = Pattern.compile(regex);
                        String lesson =lessons.get(0);
                        boolean containsNumber = pattern.matcher(lesson).matches();
                        if(!containsNumber)
                        {
                            saveStatus.put(Utility.STATUS,"false");
                            saveStatus.put(Utility.MESSAGE,"There is no lesson for this course! ");
                            return saveStatus;
                        }
                    }
                    for (int i = 0; i < lessons.size(); i++) {
                        if (lessons.get(i).equals("There i")) {
                            lessons.remove(i);
                        }

                    }
                    enrollUserCourse.put(s,lessons);
                }
                success = setUserCourses(toEnrollCourse);

            } catch (Exception e)
            {
                Log.e(getClass().getSimpleName(), e.getMessage());
            }

            if(success)
            {
                saveStatus.put(Utility.STATUS,"true");
                saveStatus.put(Utility.MESSAGE,"Save Successful!");
            }else
            {
                saveStatus.put(Utility.STATUS,"false");
                saveStatus.put(Utility.MESSAGE,"Save failed, please try again! ");
            }


        }


        return saveStatus;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to check course requisite and incompatibility
     *
     *
     */
    public Map<String, String> isCourseConflict(String courseKey) { //check course requisite and incompatibility
        Map<String, String> conflict = new HashMap<>();

        try {
            String courseId = courseKey.replaceAll("_S1", "").replaceAll("_S2", "");
            Map<String, String> compatibility = this.compatibilityInstance.getCoursesCompatiblityById(courseId);
            String requisite = compatibility.get(Utility.REQUISITE);
            String incompatibility = compatibility.get(Utility.INCOMPATIBILITY);

            List<String> requisiteCourses = new ArrayList<>();
            List<String> incompatibilityCourses = new ArrayList<>();

            requisiteCourses = getCompatibilityList(requisite); // get requisite
            incompatibilityCourses = getCompatibilityList(incompatibility); //get incompatibility


            //Check if incompatibility courses have been enrolled
            for (int i = 0; i < incompatibilityCourses.size(); i++) {
                boolean isIncompatibleCourseEnrolled = false;

                String incompatibleCourseKey = incompatibilityCourses.get(i);
                isIncompatibleCourseEnrolled = isEnrolledCourse(incompatibleCourseKey);

                //replace COMP1110 to true or false
                incompatibility = incompatibility.replaceAll(incompatibleCourseKey, String.valueOf(isIncompatibleCourseEnrolled));
            }

            //Check if requisite courses have been enrolled
            for (int i = 0; i < requisiteCourses.size(); i++) {
                boolean isRequisiteEnrolled = false;

                String requisiteCourseKey = requisiteCourses.get(i);
                isRequisiteEnrolled = isEnrolledCourse(requisiteCourseKey);

                //replace COMP1110 to 'true' or 'false'
                requisite = requisite.replaceAll(requisiteCourseKey, String.valueOf(isRequisiteEnrolled));
            }

            Boolean isRequisited = true;
            Boolean isIncompatibility = false;

            if (!requisite.equals(""))
                isRequisited = stringBooleanExpression(requisite);
            if (!incompatibility.equals(""))
                isIncompatibility = stringBooleanExpression(incompatibility);

            //both Requisite and incompatibility are not satisfied
            String rMessage = compatibility.get(Utility.REQUISITE);
            String iMessage = compatibility.get(Utility.INCOMPATIBILITY);
            String or = Pattern.quote("||");
            String and = Pattern.quote("&&");
            if (!isRequisited && isIncompatibility) {//course has both requisite and incompatibility course

                rMessage = rMessage.replaceAll(or, "OR").replaceAll(and, "AND");
                iMessage = iMessage.replaceAll(or, "OR").replaceAll(and, "AND");
                conflict.put(Utility.STATUS, "false");
                conflict.put(Utility.MESSAGE, "To enrol in this course you must have completed " + rMessage
                        + "; and You are not able to enrol in this course if you have completed " + iMessage);
            } else if (!isRequisited) {//there are requisite course need to be enrolled first
                rMessage = rMessage.replace(or, "OR").replace(and, "AND");
                conflict.put(Utility.STATUS, "false");
                conflict.put(Utility.MESSAGE, "To enrol in this course you must have completed " + rMessage);
                return conflict;
            } else if (isIncompatibility) {//there are incompatibility course been enrolled
                iMessage = iMessage.replaceAll(or, "OR").replaceAll(and, "AND");
                conflict.put(Utility.STATUS, "false");
                conflict.put(Utility.MESSAGE, "You are not able to enrol in this course if you have completed " + iMessage);
                return conflict;
            }

        } catch (Exception e) {

        }
        return conflict;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to retrieve all the course compatibility information
     *
     *
     */
    //get list of compatibility "COMP3670||( (COMP1110 || COMP1140 )&&( MATH1014 || MATH1115))"
    public List<String> getCompatibilityList(String compatibility) {
        List<String> courseMatches = new ArrayList<>();

        //pattern COMP1110, COMP1100, COMP6442
        //String rexPattern = "\\.*COMP\\d+";
        String rexPattern = "[A-Za-z]{4}\\d{4}";
        Pattern pattern = Pattern.compile(rexPattern);
        Matcher match = pattern.matcher(compatibility);

        while (match.find())
            courseMatches.add(match.group());

        return courseMatches;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is to check if course has been enrolled
     *
     *
     */
    //check if the course has been enrolled
    public boolean isEnrolledCourse(String courseKey) {
        boolean isEnrolledCourse = false;

        try {
            Iterator<String> courseIds = this.userCourses.keys();
            while (courseIds.hasNext()) {
                String courseId = courseIds.next();

                if (courseId.contains(courseKey))
                    isEnrolledCourse = true;
            }
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
        return isEnrolledCourse;
    }

    /**
     * @author Jingwei Wang (u6891978)
     *
     * This method is convert String (False || (True && False)) into Boolean format
     *
     *
     */
    //    convert String (false|| (false && true)) to Boolean
    public Boolean stringBooleanExpression(String toBoolean) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            Object result = engine.eval(toBoolean);
            return Boolean.TRUE.equals(result);

        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), e.getMessage());
        }
        return false;
    }

}
