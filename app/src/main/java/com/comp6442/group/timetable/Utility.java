package com.comp6442.group.timetable;

public class Utility {
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
