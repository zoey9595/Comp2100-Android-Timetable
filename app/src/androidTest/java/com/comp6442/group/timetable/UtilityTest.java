package com.comp6442.group.timetable;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class UtilityTest {

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method  compareTimeInString
     */
    @Test
    public void compareTimeInStringTest() {
        String time1="13:00";
        String time2="14:00";
        long timeDiscrepency1 = Utility.compareTimeInString(time1,time2);
        long timeDiscrepency2 = Utility.compareTimeInString(time2,time1);
        long timeDiscrepency3 = Utility.compareTimeInString(time1,time1);
        assert (timeDiscrepency1 <0);
        assert (timeDiscrepency2 >0);
        assertEquals(timeDiscrepency3, 0);

    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method  compareTimeInString
     */
    @Test
    public void getWeekdayList() {
        List<String> weekdays = new ArrayList<>();
        List<String> weekday = new ArrayList<>();
        weekdays.add("Mon");
        weekdays.add("Tue");
        weekdays.add("Wed");
        weekdays.add("Thu");
        weekdays.add("Fri");

        weekday = Utility.getWeekdayList();
        assertArrayEquals(weekday.toArray(), weekdays.toArray());

    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method  WeekdayDisplay
     */
    @Test
    public void WeekdayDisplayTest() {
        String mon = "Mon";
        String tue = "Tue";
        String wed = "Wed";
        String thu = "Thu";
        String fri = "Fri";

        String toMon= Utility.WeekdayDisplay("Monday");
        String toTue= Utility.WeekdayDisplay("Tuesday");
        String toWed= Utility.WeekdayDisplay("Wednesday");
        String toThu= Utility.WeekdayDisplay("Thursday");
        String toFri= Utility.WeekdayDisplay("Friday");

        assertSame(mon,toMon);
        assertSame(tue,toTue);
        assertSame(wed,toWed);
        assertSame(thu,toThu);
        assertSame(fri,toFri);

    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method  getLessonTypeList
     */
    @Test
    public void getLessonTypeListTest() {
        List<String> lessonType = new ArrayList<>();

        lessonType = Utility.getLessonTypeList();
        assertEquals(lessonType.size(), 21);

    }

}
