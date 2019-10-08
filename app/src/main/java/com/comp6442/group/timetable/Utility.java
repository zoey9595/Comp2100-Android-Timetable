package com.comp6442.group.timetable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static final String STATUS = "status";
    public static final String MESSAGE = "message";

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

    public static long compareTimeInString(String time1, String time2){
        long elapsed =0;

        try{
//            String startTime = "10:00";
//            String endTime = "12:00";
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date d1 = sdf.parse(time1);
            Date d2 = sdf.parse(time2);
            elapsed = d1.getTime() - d2.getTime();
            System.out.println(elapsed);

        }
        catch (ParseException e)
        {

        }
        return elapsed;
    }
}
