package com.comp6442.group.timetable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    //Compatibility
    public static final String REQUISITE = "requisite";
    public static final String INCOMPATIBILITY = "incompatibility";

    //weekdays
    public static final String MON = "Mon";
    public static final String TUE = "Tue";
    public static final String WED = "Wed";
    public static final String THU = "Thu";
    public static final String FRI = "Fri";


    //lessontypes
    public static final String LEC = "Lec";
    public static final String TUT = "Tut";
    public static final String WOR = "Wor";
    public static final String SEM = "Sem";
    public static final String COM = "Com";
    public static final String PRA = "Pra";
    public static final String STU = "Stu";
    public static final String LAN = "Lan";
    public static final String PRAC = "Prac";
    public static final String FIE = "Fie";
    public static final String LET = "Let";
    public static final String DRO = "Dro";
    public static final String EXA = "Exa";
    public static final String SUP = "Sup";
    public static final String AFF = "Aff";
    public static final String WAR = "War";
    public static final String FIL = "FIL";
    public static final String FIELDWORK = "Fieldwork";
    public static final String ONL = "Onl";
    public static final String ADH = "AdH";
    public static final String SCR = "Scr";


    public static String WeekdayDisplay(String weekdays)
    {
        String weekday="";

        switch (weekdays){
            case "Monday":
                weekday = MON;
                break;
            case "Tuesday":
                weekday = TUE;
                break;
            case "Wednesday":
                weekday = WED;
                break;
            case "Thursday":
                weekday = THU;
                break;
            case "Friday":
                weekday = FRI;
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

    //get weekday list
    public static List<String>  getWeekdayList()
    {
        List<String> weekdays = new ArrayList<>();
        weekdays.add(MON);
        weekdays.add(TUE);
        weekdays.add(WED);
        weekdays.add(THU);
        weekdays.add(FRI);
        return weekdays;
    }

    //get weekday list
    public static List<String>  getLessonTypeList()
    {
        List<String> lessonTpye = new ArrayList<>();
        lessonTpye.add(LEC);
        lessonTpye.add(TUT);
        lessonTpye.add(WOR);
        lessonTpye.add(SEM);
        lessonTpye.add(COM);
        lessonTpye.add(PRA);
        lessonTpye.add(STU);
        lessonTpye.add(LAN);
        lessonTpye.add(FIE);
        lessonTpye.add(LET);
        lessonTpye.add(DRO);
        lessonTpye.add(EXA);
        lessonTpye.add(SUP);
        lessonTpye.add(AFF);
        lessonTpye.add(WAR);
        lessonTpye.add(FIL);
        lessonTpye.add(ONL);
        lessonTpye.add(ADH);
        lessonTpye.add(SCR);
        lessonTpye.add(PRAC);
        lessonTpye.add(FIELDWORK);
        return lessonTpye;
    }


}
