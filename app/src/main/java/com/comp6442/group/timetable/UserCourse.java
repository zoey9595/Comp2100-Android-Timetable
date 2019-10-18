/**
 * The UserCourse class implements methods for reading
 * user selected courses, and calculating the active
 * lessons for current week. The data will be used to
 * render the timetable calender.
 *
 * @author  Yongchao Lyu (u6874539)
 * @version 1.0
 * @since   2019-10-01
 */

package com.comp6442.group.timetable;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCourse {

    private User userInstance;
    private Course courseInstance;

    // Three level keys: CourseID, LessonName, LessonAttribute
    private Map<String, List<Map<String, String>>> userCourses = new HashMap<>();

    /**
     * Constructor of this class, do some initialising jobs
     * 1. Read user selected lessons, and lessons details
     * 2. Intersect the user selected lessons and its details
     *
     * @param context, Context: the context of this application
     */
    UserCourse(Context context) {
        this.userInstance = User.getUserInstance(context);
        this.courseInstance = Course.getCourseInstance(context);

        Map<String, List<String>> rawUserCourses = userInstance.getUserCourses();
        for (String courseID : rawUserCourses.keySet()) {
            List<Map<String, String>> userLesson = new ArrayList<>();

            List<String> userLessonNames = rawUserCourses.get(courseID);
            List<Map<String, String>> lessons = this.courseInstance.getLessons(courseID);

            for (Map<String, String> lesson : lessons)
                if (isUserLesson(lesson.get("name"), userLessonNames))
                    userLesson.add(lesson);

            this.userCourses.put(courseID, userLesson);
        }
    }

    /**
     * Judge whether a given lesson is selected by the user
     *
     * @param LessonName, String: the lesson name of a given lesson
     * @param userLessonNames, List<String>: the list of Lesson names selected by the user
     */
    private boolean isUserLesson(String LessonName, List<String> userLessonNames) {
        for (String userLessonName : userLessonNames)
            if (LessonName.contains(userLessonName))
                return true;
        return false;
    }

    /**
     * Calculate current week number using timestamp
     *
     * @return integer, the week number of current week
     *
     */
    public static int getCurrentWeek() {
        Date date = new Date();
        long week1_timestamp = 1546174800;
        long current_timestamp = date.getTime() / 1000;
        return (int) (((current_timestamp - week1_timestamp) / 604800) + 1);
    }

    /**
     * Analyse the week string, such as 30-35,38-43
     * and generate a integer list for representing
     * all active week numbers
     *
     * @param weekExpr, String: week expression for displaying active weeks, e.g. 30-35,38-43
     * @return List<Integer>, a list of active week numbers
     *
     */
    public static List<Integer> getActiveWeeks(String weekExpr) {
        List<Integer> activeWeeks = new ArrayList<>();
        for (String weekItem : weekExpr.replace(" ", "").split(",")) {
            if (weekItem.contains("-")) {
                String[] weekRange = weekItem.split("-");
                int rStart = Integer.parseInt(weekRange[0]);
                int rEnd = Integer.parseInt(weekRange[1]) + 1;
                for (int r = rStart; r < rEnd; r++)
                    activeWeeks.add(r);
            } else {
                activeWeeks.add(Integer.parseInt(weekItem));
            }
        }
        return activeWeeks;
    }

    /**
     * Calculate and record all active lessons for a certain week
     *
     * @param currentWeek, integer: the week number of current week
     * @return List<Map<String, String>>, a list of lesson details that active on this week
     *
     */
    public List<Map<String, String>> getActiveLessons(int currentWeek) {
        List<Map<String, String>> activeLessons = new ArrayList<>();
        for (String courseId: this.userCourses.keySet()) {
            List<Map<String, String>> lessons = this.userCourses.get(courseId);
            for (Map<String, String> lesson: lessons) {
                if (getActiveWeeks(lesson.get("weeks"))
                        .contains(currentWeek))
                    activeLessons.add(lesson);
            }
        }
        return activeLessons;
    }
}
