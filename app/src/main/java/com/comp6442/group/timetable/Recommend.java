/**
 * The Recommend class is a subclass of FileOperator, which implements
 * methods for operating the specialisation.json file. This file is migrated
 * to the internal storage if it has not been done yet when initialising
 * this class. After that, all operation about the file is the copy that
 * locates at the internal storage. The class contains lots of useful
 * methods that get or set different part of the specialisation data structure.
 *
 * @author  Yongchao Lyu (u6874539)
 * @version 1.0
 * @since   2019-10-10
 */

package com.comp6442.group.timetable;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Recommend extends FileOperator {

    private static Recommend recommendInstance = null;

    private User userInstance;
    private Course courseInstance;
    private JSONObject specialisation = new JSONObject();

    /**
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
    private Recommend(Context context) {
        super(context, "specialisation.json");

        this.userInstance = User.getUserInstance(context);
        this.courseInstance = Course.getCourseInstance(context);

        try {
            this.placeInternalFile(R.raw.specialisation);
            String jsonString = this.readInternalFile();
            if (jsonString != null)
                this.specialisation = new JSONObject(jsonString);

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }
    }

    /**
     * Method for getting the instance of this singleton class
     *
     * Note: The class can only be instantiated once for preventing the file
     * to be read several times. This kind of design fobidden read the same file
     * more than once such that the limited memory of the device can be saved.
     *
     * @param context context of the application
     *
     */
    public static Recommend getRecommendInstance(Context context) {
        if (recommendInstance == null)
            recommendInstance = new Recommend(context);
        return recommendInstance;
    }

    /**
     * Count the courses that appear in a certain specialisation
     *
     * @param sName, String: the name of a certain specialisation
     * @param enrolledCourses List<String>: the list of courses user selected
     * @return integer, the number of courses that exists
     * in the course list of a certain specialisation
     *
     */
    private int countMatchCourses(String sName, List<String> enrolledCourses) {
        int count = 0;

        try {
            List<String> specialCourses = convertJSONArray(
                    (JSONArray) this.specialisation.get(sName));

            for (String enrolledCourse : enrolledCourses)
                for (String specialCourse : specialCourses)
                    if (specialCourse.contains(enrolledCourse)) {
                        count++;
                        break;
                    }

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }

        return count;
    }

    /**
     * Find the best matched specialisation name according to the user enrolled courses
     *
     * @param enrolledCourses, List<String>: the courses that user enrolled
     * @return String, the best matched specialisation name
     *
     */
    private String getBestMatchSpecialisation(List<String> enrolledCourses) {
        int maxCount = 0;
        String bestMatch = "";

        Iterator<String> sNames = this.specialisation.keys();
        while (sNames.hasNext()) {
            String sName = sNames.next();
            if (!sName.equals("basicCourses")) {
                int count = countMatchCourses(sName, enrolledCourses);
                if (count > maxCount) {
                    maxCount = count;
                    bestMatch = sName;
                }
            }
        }

        return bestMatch;
    }

    /**
     * Check whether a course has been enrolled
     *
     * @param course String: course name to be checked
     * @param enrolledCourses List<String>: a list of courses that have enrolled
     * @return boolean, true if the course has not yet enrolled
     *
     */
    private boolean isCandidate(String course, List<String> enrolledCourses) {
        for (String enrolled : enrolledCourses)
            if (course.contains(enrolled))
                return false;
        return true;
    }

    /**
     * Get all courses that have not been enrolled yet
     *
     * @param enrolledCourses List<String>: a list cf courses that have enrolled
     * @param courseList List<String>: a list of all available courses
     * @return List<String>, a list of courses that have not yet enrolled
     *
     */
    private List<String> getCandidates(List<String> enrolledCourses, List<String> courseList) {
        List<String> candidateCourses = new ArrayList<>();
        for (String course : courseList)
            if (isCandidate(course, enrolledCourses))
                candidateCourses.add(course.split("/")[0]);
        return candidateCourses;
    }

    /**
     * Convert JSONArray data structure to List
     *
     * @param courseArray JSONArray to be converted
     * @return List<String>, List data type with the same content of the input
     *
     */
    private List<String> convertJSONArray(JSONArray courseArray) {
        List<String> courseList = new ArrayList<>();

        try {
            for (int index = 0; index < courseArray.length(); index++)
                courseList.add((String) courseArray.get(index));

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }

        return courseList;
    }

    /**
     * Filter the candidate courses and keep the courses available in certain semester
     *
     * @param candidateCourses List<String>: candidate courses without semester info
     * @return List<String>, available courses filtered from the input appended with semester
     *
     */
    private List<String> filterAvailableCourses(List<String> candidateCourses) {
        List<String> availableCourses = new ArrayList<>();
        for (String candidate: candidateCourses)
            for (String course: this.courseInstance.getCompCourseNameList())
                if (course.contains(candidate) &&
                        !availableCourses.contains(course))
                    availableCourses.add(course);
        return availableCourses;
    }

    /**
     * Main functional method of this class for generating a recommendation
     *
     * @return List<String>, a list of courses recommended to the user
     *
     */
    public List<String> getRecommendCourses() {
        List<String> enrolledCourses = new ArrayList<>();
        List<String> recommendCourses = new ArrayList<>();

        for (String courseID : this.userInstance.getUserCourses().keySet())
            enrolledCourses.add(courseID.split("_S")[0]);
        String bestMatch = getBestMatchSpecialisation(enrolledCourses);

        try {
            List<String> basicCourseList = convertJSONArray(
                    (JSONArray) this.specialisation.get("basicCourses"));
            List<String> candidateCourses = getCandidates(enrolledCourses, basicCourseList);

            if (!bestMatch.isEmpty()) {
                List<String> specialCourseList = convertJSONArray(
                        (JSONArray) this.specialisation.get(bestMatch));
                candidateCourses.addAll(getCandidates(enrolledCourses, specialCourseList));
            }

            recommendCourses = filterAvailableCourses(candidateCourses);

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }

        return recommendCourses;
    }
}
