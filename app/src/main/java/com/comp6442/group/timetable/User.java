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

    public Map<String, List> getUserCourses() {
        Map<String, List> selectedCourses = new HashMap<>();
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
}
