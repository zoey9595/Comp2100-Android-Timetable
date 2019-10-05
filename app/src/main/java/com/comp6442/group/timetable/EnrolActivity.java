/**
 * Author: Yuqing Zhai
 * UID： u6865190
 */
package com.comp6442.group.timetable;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class EnrolActivity extends AppCompatActivity {

    private TextView mTvCourseID, mTvLectureDetails, mTvEnrolledCourses;
    private Button mBtnSearchCourse, mBtnSearchTutorial, mBtnEnrol, mBtnCancel, mBtnAdd;
    private ArrayList<String> courseList, lectureList, tutorialList;
    private String courseID;
    private HashMap<String, ArrayList<String>> userCourseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrol);
        setTitle("Enrol");

        // Set up variables
        mTvCourseID = findViewById(R.id.tv_courseID);
        mTvLectureDetails = findViewById(R.id.tv_lectureDetails);
        mTvEnrolledCourses = findViewById(R.id.tv_enrolledCourses);
        mBtnEnrol = findViewById(R.id.btn_enrol);
        mBtnCancel = findViewById(R.id.btn_cancel);
        mBtnAdd = findViewById(R.id.btn_addnewcourse);
        mBtnSearchCourse = findViewById(R.id.btn_course);
        mBtnSearchTutorial = findViewById(R.id.btn_tutorial);
        final ScrollView scrollView = findViewById(R.id.scrollable);
        final LinearLayout linearLayout = findViewById(R.id.ll_scroll);

        // Get a list containing all ANU courses
        final Course course = Course.getCourseInstance(this);
        courseList = (ArrayList) course.getCourseList();

        // Get a list containing all student enrolled courses
        final User user = User.getUserInstance(this);
        userCourseList = (HashMap) user.getUserCourses();
        // Display enrolled courses
        StringBuilder enrolledBuilder = new StringBuilder();
        for (String s : userCourseList.keySet()) {
            String courseName = course.getCourseName(s);
            enrolledBuilder.append(s + " - " + courseName + "\n");
        }
        mTvEnrolledCourses.setText(enrolledBuilder.toString());

        // Make lecture details can be scrolled
        mTvLectureDetails.setMovementMethod(new ScrollingMovementMethod());

        // Set up an OnClickListener on the searchview
        mBtnSearchCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View searchableView = inflater.inflate(R.layout.searchable_list_dialog, null);
                final ListView mLvCourse = searchableView.findViewById(R.id.lv_course);
                final SearchView mSvCourse = searchableView.findViewById(R.id.sv_course);

                // Set adapter
                final MyAdapter myAdapter = new MyAdapter(view.getContext(), course, courseList);
                mLvCourse.setAdapter(myAdapter);
                mLvCourse.setTextFilterEnabled(true);

                // Make course searchview listen to text change
                mSvCourse.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String newText) {
                        mSvCourse.clearFocus();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (TextUtils.isEmpty(newText)) {
                            mLvCourse.clearTextFilter();
                            //myAdapter.getFilter().filter(null);
                        } else {
                            //mLvCourse.setFilterText(newText);
                            myAdapter.getFilter().filter(newText);
                        }
                        return true;
                    }
                });
                // make listview show the selected course lecture time
                mLvCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        courseID = (String) myAdapter.getItem(i);
                        mTvCourseID.setText(courseID);
                        lectureList = (ArrayList) course.getLecDetails(courseID);
                        StringBuilder builder = new StringBuilder();
                        for (String s : lectureList) {
                            builder.append(s + "\n");
                        }
                        mTvLectureDetails.setText(builder.toString());
                        linearLayout.removeAllViews();
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(searchableView);
                dialog.show();
            }
        });

        // dynamically increase the amount of tutorial checkboxes
        mBtnSearchTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.removeAllViews();
                tutorialList = (ArrayList) course.getTutWorDetails(courseID);
                for (int i = 0; i < tutorialList.size(); i++) {
                    CheckBox checkBox = new CheckBox(view.getContext());
                    checkBox.setId(i);
                    checkBox.setText(tutorialList.get(i));
                    checkBox.setTextColor(-1979711488);
                    linearLayout.addView(checkBox);
                }
            }
        });
        // go to AddActivity (Xiaochan Zhang)
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(EnrolActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }
}
