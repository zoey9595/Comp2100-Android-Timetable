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

    public static Recommend getRecommendInstance(Context context) {
        if (recommendInstance == null)
            recommendInstance = new Recommend(context);
        return recommendInstance;
    }

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

    private String getBestMatchSpecialisation(List<String> enrolledCourses) {
        int maxCount = 0;
        String bestMatch = "";

        Iterator<String> sNames = this.specialisation.keys();
        while (sNames.hasNext()) {
            String sName = sNames.next();
            int count = countMatchCourses(sName, enrolledCourses);
            if (count > maxCount) {
                maxCount = count;
                bestMatch = sName;
            }
        }

        return bestMatch;
    }

    private boolean isCandidate(String course, List<String> enrolledCourses) {
        for (String enrolled : enrolledCourses)
            if (course.contains(enrolled))
                return false;
        return true;
    }

    private List<String> getCandidates(List<String> enrolledCourses, List<String> courseList) {
        List<String> candidateCourses = new ArrayList<>();
        for (String course : courseList)
            if (isCandidate(course, enrolledCourses))
                candidateCourses.add(course.split("/")[0]);
        return candidateCourses;
    }

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

    private List<String> filterAvailableCourses(List<String> candidateCourses) {
        List<String> availableCourses = new ArrayList<>();
        for (String candidate: candidateCourses)
            for (String course: this.courseInstance.getCompCourseNameList())
                if (course.contains(candidate) &&
                        !availableCourses.contains(course)) {
                    availableCourses.add(course);
                    break;
                }
        return availableCourses;
    }

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

        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage());
        }

        return recommendCourses;
    }
}
