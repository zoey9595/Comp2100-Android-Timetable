package com.comp6442.group.timetable;

public class CourseDetailInfo {


    private String mLessonType, mLessonAlph, mLessonNum, mLessonStart, mLessonEnd, mLessonDay, mCourseName, mCourseID;

    public CourseDetailInfo(String lessonType, String lessonAlph, String lessonNum, String lessonDay, String lessonStart, String lessonEnd){

        mLessonType = lessonType;
        mLessonAlph = lessonAlph;
        mLessonNum = lessonNum;
        mLessonDay = lessonDay;
        mLessonStart = lessonStart;
        mLessonEnd = lessonEnd;
    }

    public String getLessonType() {
        return mLessonType;
    }

    public String setLessonType(String str){
        mLessonType = str;
        return mLessonType;
    }

    public String getLessonAlph() {
        return mLessonAlph;
    }

    public String setLessonAlph(String str){
        mLessonAlph = str;
        return mLessonAlph;
    }

    public String getLessonNum() {
        return mLessonNum;
    }

    public String setLessonNum(String str){
        mLessonNum = str;
        return mLessonNum;
    }

    public String getLessonDay() {
        return mLessonDay;
    }

    public String setLessonDay(String str){
        mLessonDay = str;
        return mLessonDay;
    }

    public String getLessonStart() {
        return mLessonStart;
    }

    public String setLessonStart(String str){
        mLessonStart = str;
        return mLessonStart;
    }

    public String getLessonEnd() {
        return mLessonEnd;
    }

    public String setLessonEnd(String str){
        mLessonEnd = str;
        return mLessonEnd;
    }

}
