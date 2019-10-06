package com.comp6442.group.timetable;

public class Utility {

    //Lesson Properties
    public static final String FULL_NAME = "name";
    public static final String NAME_TYPE = "nameType";
    public static final String NAME_ALP = "nameAlphabet";
    public static final String NAME_INDEX = "nameIndex";
    public static final String WEEKDAY = "weekday";
    public static final String START = "start";
    public static final String END = "end";

    public static final String COURSE_NAME = "courseName";

    //Lecture Type
    public static final String LEC = "Lec";

    public static String WeekdayDisplay(String weekdays)
    {
        String weekday="";

        switch (weekdays){
            case "Monday":
                weekday = "Mon";
                break;
            case "Tuesday":
                weekday = "Tue";
                break;
            case "Wednesday":
                weekday = "Wed";
                break;
            case "Thursday":
                weekday = "Thu";
                break;
            case "Friday":
                weekday = "Mon";
                break;
        }


        return weekday;
    }
}
