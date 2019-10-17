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
        long timeDiscrepencyCase1 = Utility.compareTimeInString(time1,time2);
        long timeDiscrepencyCase2 = Utility.compareTimeInString(time2,time1);
        long timeDiscrepencyCase3 = Utility.compareTimeInString(time1,time1);
        assert (timeDiscrepencyCase1 <0);
        assert (timeDiscrepencyCase2 >0);
        assertEquals(timeDiscrepencyCase3, 0);

    }

    /**
     * @author  Jingwei Wang(u6891978)
     *
     * Test for the method  getWeekdayList
     */
    @Test
    public void getWeekdayListTest() {
        List<String> weekday = new ArrayList<>();
        List<String> expectWeekday = new ArrayList<>();
        weekday.add("Mon");
        weekday.add("Tue");
        weekday.add("Wed");
        weekday.add("Thu");
        weekday.add("Fri");

        expectWeekday = Utility.getWeekdayList();
        assertArrayEquals(weekday.toArray(), expectWeekday.toArray());

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

        String expectedMon= Utility.WeekdayDisplay("Monday");
        String expectedTue= Utility.WeekdayDisplay("Tuesday");
        String expectedWed= Utility.WeekdayDisplay("Wednesday");
        String expectedThu= Utility.WeekdayDisplay("Thursday");
        String expectedFri= Utility.WeekdayDisplay("Friday");

        assertSame(mon,expectedMon);
        assertSame(tue,expectedTue);
        assertSame(wed,expectedWed);
        assertSame(thu,expectedThu);
        assertSame(fri,expectedFri);

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
