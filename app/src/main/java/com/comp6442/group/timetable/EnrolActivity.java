package com.comp6442.group.timetable;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class EnrolActivity extends AppCompatActivity {

    private TextView mTvCourseID, mTvLectureDetails;
    private Button mBtnSearchCourse, mBtnSearchTutorial, mBtnEnrol, mBtnCancel, mBtnAdd;
    private ArrayList courseList, lectureList, tutorialList;
    private String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrol);
        setTitle("Enrol");

        // Set up variables
        mTvCourseID = findViewById(R.id.tv_courseID);
        mTvLectureDetails = findViewById(R.id.tv_lectureDetails);
        mBtnEnrol = findViewById(R.id.btn_enrol);
        mBtnCancel = findViewById(R.id.btn_cancel);
        mBtnAdd = findViewById(R.id.btn_add);
        mBtnSearchCourse = findViewById(R.id.btn_course);
        mBtnSearchTutorial = findViewById(R.id.btn_tutorial);
        final ScrollView scrollView = findViewById(R.id.scrollable);
        final LinearLayout linearLayout = findViewById(R.id.ll_scroll);

        // Get a list containing all ANU courses
        final Course course = Course.getCourseInstance(this);
        courseList = (ArrayList) course.getCourseList();

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
                final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, courseList);
                mLvCourse.setAdapter(stringArrayAdapter);
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
                            stringArrayAdapter.getFilter().filter(null);
                        } else {
                            stringArrayAdapter.getFilter().filter(newText);
                        }
                        return true;
                    }
                });
                // make listview show the selected course lecture time
                mLvCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        courseID = stringArrayAdapter.getItem(i);
                        mTvCourseID.setText(courseID);
                        lectureList = (ArrayList) course.getLessons(courseID);
                        StringBuilder builder = new StringBuilder();
                        for (Object s : lectureList) {
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
                tutorialList = (ArrayList) course.getLessons(courseID);
                for (int i = 0; i < tutorialList.size(); i++) {
                    CheckBox checkBox = new CheckBox(view.getContext());
                    checkBox.setId(i);
                    checkBox.setText(tutorialList.get(i).toString());
                    checkBox.setTextColor(-1979711488);
                    linearLayout.addView(checkBox);
                }
            }
        });
    }
}
