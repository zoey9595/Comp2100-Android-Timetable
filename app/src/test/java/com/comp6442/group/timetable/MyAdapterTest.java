package com.comp6442.group.timetable;

import android.content.Context;
import android.view.View;

import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.*;

public class MyAdapterTest {

    private MyAdapter myAdapter;
    private MyAdapter.MyCourseHolder holder;
    private Context context;
    private Course course;
    private View view;

    @Before
    public void setUp() throws Exception {
        ArrayList<String> courseList = new ArrayList<>();
        courseList.add("COMP6710_S1");
        courseList.add("MATH6005_S1");
        myAdapter = new MyAdapter(context, course, courseList);
    }

    @Test
    public void getFilterTest() {
    }

    @Test
    public void getCountTest() {
        assertEquals(2, myAdapter.getCount());
    }

    @Test
    public void getItemTest() {
        assertEquals("COMP6710_S1", myAdapter.getItem(0));
        assertEquals("MATH6005_S1", myAdapter.getItem(1));
    }

    @Test
    public void getItemIdTest() {
        assertEquals(0, myAdapter.getItemId(0));
        assertEquals(1, myAdapter.getItemId(1));
    }

    @Test
    public void getViewTest() {
    }
}