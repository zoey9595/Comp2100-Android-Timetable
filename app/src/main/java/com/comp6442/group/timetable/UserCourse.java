package com.comp6442.group.timetable;

import android.content.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCourse {

    private User userInstance;
    private Course courseInstance;

    // Three level keys: CourseID, LessonName, LessonAttribute
    private Map<String, Map<String, Map<String, String>>> userCourses = new HashMap<>();

    UserCourse(Context context) {
        this.userInstance = User.getUserInstance(context);
        this.courseInstance = Course.getCourseInstance(context);

        Map<String, List<String>> rawUserCourses = userInstance.getUserCourses();
        for (String courseID: rawUserCourses.keySet()) {
            List<String> userLessonNames = rawUserCourses.get(courseID);
            Map<String, Map<String, String>> userLesson = new HashMap<>();

            List<Map<String, String>> lessons = this.courseInstance.getLessons(courseID);
            for (Map<String, String> lesson: lessons) {
                String lessonName = lesson.get("name");
                if (isUserLesson(lessonName, userLessonNames))
                    userLesson.put(lessonName, lesson);
            }

            this.userCourses.put(courseID, userLesson);
        }
    }

    private boolean isUserLesson(String LessonName, List<String> userLessonNames) {
        for (String userLessonName: userLessonNames)
            if (LessonName.contains(userLessonName))
                return true;
        return false;
    }
}
