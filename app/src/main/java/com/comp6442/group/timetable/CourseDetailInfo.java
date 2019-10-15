package com.comp6442.group.timetable;

import android.content.res.Resources;
import android.graphics.ColorSpace;
import android.widget.ArrayAdapter;

import java.util.Arrays;

public class CourseDetailInfo {


    private String mLessonType, mLessonAlph, mLessonNum, mLessonStart, mLessonEnd, mLessonDay, mCourseName, mCourseID;
    private String [] time = {"08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30",
            "15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00"};

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

    public int getLessonStartInt(){
        int timeStart = Arrays.binarySearch(time, mLessonStart);
        return timeStart;
    }

    public String getLessonEnd() {
        return mLessonEnd;
    }

    public int getLessonEndInt(){
        int timeEnd = Arrays.binarySearch(time, mLessonEnd);
        return timeEnd;
    }

    public String setLessonEnd(String str){
        mLessonEnd = str;
        return mLessonEnd;
    }

}
