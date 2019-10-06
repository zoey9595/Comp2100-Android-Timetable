/**
 * Author: Yuqing Zhai
 * UID： u6865190
 */
package com.comp6442.group.timetable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class EnrolActivity extends AppCompatActivity {

    private TextView mTvCourseID, mTvLectureDetails, mTvRecommend;
    private Button mBtnSearchCourse, mBtnSearchTutorial, mBtnDelete, mBtnEnrol, mBtnAdd;
    private ListView mLvEnrolledCourses, mLvTutorial, mLvRecommend;
    private ArrayList<String> courseList, lectureList, tutorialList, recommendList;
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
        mTvRecommend = findViewById(R.id.tv_titleEnrolledCourses);
        mLvEnrolledCourses = findViewById(R.id.lv_enrolledCourses);
        mLvTutorial = findViewById(R.id.lv_tutorials);
        mLvRecommend = findViewById(R.id.lv_recommendCourses);
        mBtnDelete = findViewById(R.id.btn_deleteEnroledCourses);
        mBtnEnrol = findViewById(R.id.btn_enrol);
        mBtnAdd = findViewById(R.id.btn_addnewcourse);
        mBtnSearchCourse = findViewById(R.id.btn_course);
        mBtnSearchTutorial = findViewById(R.id.btn_tutorial);

        // Get a list containing all ANU courses
        final Course course = Course.getCourseInstance(this);
        courseList = (ArrayList) course.getCourseList();

        // Get a list containing all student enrolled courses
        final User user = User.getUserInstance(this);
        userCourseList = (HashMap) user.getUserCourses();
        // Display enrolled courses
        final ArrayList<String> enrolledCourses = new ArrayList<>();
        for (String s : userCourseList.keySet()) {
            enrolledCourses.add(course.getCourseName(s));
        }
        final ArrayAdapter<String> enrolAdapter = new ArrayAdapter<>(this,
                R.layout.layout_custom_row, enrolledCourses);
        mLvEnrolledCourses.setAdapter(enrolAdapter);
        mLvEnrolledCourses.setDivider(null);

        // Make recommendation
        recommendList = new ArrayList<>();
        recommendList.add("COMP8715_S1-Computing Project");
        recommendList.add("COMP8110_S1-Managing Software Projects in a System Context");
        recommendList.add("COMP8420_S1-Neural Networks, Deep Learning and Bio-inspired Computing");
        recommendList.add("COMP8600_S1-Statistical Machine Learning\n");
        ArrayAdapter<String> recommendAdapter = new ArrayAdapter<>(this,
                android.R.layout.test_list_item, recommendList);
        mLvRecommend.setAdapter(recommendAdapter);

        // Make lecture details can be scrolled
        mTvLectureDetails.setMovementMethod(new ScrollingMovementMethod());

        // Set up an OnClickListener on the SearchView
        mBtnSearchCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View searchableView = inflater.inflate(R.layout.layout_searchable_list_dialog, null);
                final ListView mLvCourse = searchableView.findViewById(R.id.lv_course);
                final SearchView mSvCourse = searchableView.findViewById(R.id.sv_course);

                // Set adapter
                final MyAdapter myAdapter = new MyAdapter(view.getContext(), course, courseList);
                mLvCourse.setAdapter(myAdapter);
                mLvCourse.setTextFilterEnabled(true);

                // Make course SearchView listen to text change
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
                // make ListView show the selected course lecture time
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
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(searchableView);
                dialog.show();
            }
        });

        // set available tutorials ListView
        mBtnSearchTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tutorialList = (ArrayList) course.getTutWorDetails(courseID);
                ArrayAdapter<String> tutorialAdapter = new ArrayAdapter<>(view.getContext(),
                        R.layout.layout_custom_row, tutorialList);
                mLvTutorial.setAdapter(tutorialAdapter);
                mLvTutorial.setDivider(null);
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

        // Set delete button
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EnrolActivity.this);
                alertDialog.setTitle("Delete");
                alertDialog.setIcon(R.drawable.icon_delete);
                alertDialog.setMessage("Do you really want to delete this course?");
                alertDialog.setCancelable(true);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < mLvEnrolledCourses.getCount(); j++) {
                            CheckedTextView mCtv = (CheckedTextView) mLvEnrolledCourses
                                    .getChildAt(j - mLvEnrolledCourses.getFirstVisiblePosition());
                            String temp = mCtv.getText().toString();
                            if (mCtv.isChecked()) {
                                // Delete selected courses from user.json
                                enrolAdapter.remove(temp);
                                enrolAdapter.notifyDataSetChanged();
                                Toast.makeText(getBaseContext(), "position " + j + " is checked.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        // Set enrol button
        mBtnEnrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EnrolActivity.this);
                alertDialog.setTitle("Enrol");
                alertDialog.setIcon(R.drawable.icon_enrol_course);
                alertDialog.setMessage("Do you really want to enrol this course?");
                alertDialog.setCancelable(true);
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList<String> newEnrolCourses = new ArrayList<>();
                        String selectedcourseID = mTvCourseID.getText().toString();
                        String selectedCourseDetails = mTvLectureDetails.getText().toString().substring(0, 7);
                        newEnrolCourses.add(selectedCourseDetails);
                        SparseBooleanArray checked = mLvTutorial.getCheckedItemPositions();
                        if (checked != null) {
                            for (int j = 0; j < checked.size(); j++) {
                                if (checked.valueAt(j)) {
                                    String selectedTutorials = (String) mLvTutorial.getItemAtPosition(checked.keyAt(j));
                                    selectedTutorials = selectedTutorials.substring(0, 7);
                                    newEnrolCourses.add(selectedTutorials);
                                }
                            }
                        }
                        // Add the selected course to userCourseList
                        userCourseList.put(selectedcourseID, newEnrolCourses);
                        System.out.println(userCourseList.toString());
                        // Return to the main activity
                        finish();
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
            }

            // Recommendation algorithm
            private ArrayList<String> recommendCourses(ArrayList<String> enrolledCourses) {
                return enrolledCourses;
            }
        });
    }
}
