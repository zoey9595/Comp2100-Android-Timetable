/**
 * Author: Yuqing Zhai
 * UID： u6865190
 * <p>
 * This is the enrol page. You can find the enrolled courses list and the recommendation course list.
 * You can search any ANU courses in this page, and add it to the calender page.
 * If there are any conflicts between the course you select and the courses you have enrolled, you
 * will get a notice.
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
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnrolActivity extends AppCompatActivity {

    // Set up all elements
    private TextView mTvCourseID, mTvLectureDetails, mTvRecommend;
    private Button mBtnSearchCourse, mBtnSearchTutorial, mBtnDelete, mBtnEnrol, mBtnAdd;
    private ListView mLvEnrolledCourses, mLvTutorial, mLvRecommend;
    private LinearLayout mLl1, mLl2, mLl3;
    private ArrayList<String> courseList, lectureList, tutorialList;
    private String courseID;
    private HashMap<String, ArrayList<String>> userCourseList;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrol);
        // Reset the page name
        setTitle("Enrol");

        // Set up local variables
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
        mLl1 = findViewById(R.id.ll1);
        mLl2 = findViewById(R.id.ll2);
        mLl3 = findViewById(R.id.ll3);

        // Set items height to fit different devices size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenHeight = dm.heightPixels;

        RelativeLayout.LayoutParams mLl1Params = (RelativeLayout.LayoutParams) mLl1.getLayoutParams();
        mLl1Params.height = screenHeight / 20;
        mLl1.setLayoutParams(mLl1Params);

        RelativeLayout.LayoutParams mLl2Params = (RelativeLayout.LayoutParams) mLl2.getLayoutParams();
        mLl2Params.height = screenHeight / 20;
        mLl2.setLayoutParams(mLl2Params);

        RelativeLayout.LayoutParams mLl3Params = (RelativeLayout.LayoutParams) mLl3.getLayoutParams();
        mLl3Params.height = screenHeight / 20;
        mLl3.setLayoutParams(mLl3Params);

        final RelativeLayout.LayoutParams mLvEnrolledCoursesLayoutParams = (RelativeLayout.LayoutParams) mLvEnrolledCourses.getLayoutParams();
        mLvEnrolledCoursesLayoutParams.height = screenHeight / 8;
        mLvEnrolledCourses.setLayoutParams(mLvEnrolledCoursesLayoutParams);

        RelativeLayout.LayoutParams mLvRecommendLayoutParams = (RelativeLayout.LayoutParams) mLvRecommend.getLayoutParams();
        mLvRecommendLayoutParams.height = screenHeight / 8;
        mLvRecommend.setLayoutParams(mLvRecommendLayoutParams);

        // Get a list containing all ANU courses from Course
        final Course course = Course.getCourseInstance(this);
        courseList = (ArrayList) course.getUnEnrolledCourseList(this);

        // Get a list containing all student enrolled courses from User
        final User user = User.getUserInstance(this);
        userCourseList = (HashMap) user.getUserCourses();
        // Display enrolled courses
        final ArrayList<String> enrolledCourses = new ArrayList<>();
        for (String s : userCourseList.keySet()) {
            enrolledCourses.add(s + ": " + course.getCourseName(s));
        }
        final ArrayAdapter<String> enrolAdapter = new ArrayAdapter<>(this,
                R.layout.layout_custom_row, enrolledCourses);
        mLvEnrolledCourses.setAdapter(enrolAdapter);
        mLvEnrolledCourses.setDivider(null);

        // Make recommendation based on Recommend class
        Recommend recommend = Recommend.getRecommendInstance(this);
        ArrayList<String> recommendList = (ArrayList<String>) recommend.getRecommendCourses();
        MyAdapter recommendAdapter = new MyAdapter(this, course, recommendList);
        mLvRecommend.setAdapter(recommendAdapter);

        // Make the TextView containing lecture details scrollable
        mTvLectureDetails.setMovementMethod(new ScrollingMovementMethod());

        // Set up the OnClickListener on the search button
        mBtnSearchCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Generate a new dialog to show search process
                final Dialog dialog = new Dialog(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View searchableView = inflater.inflate(R.layout.layout_searchable_list_dialog, null);
                final ListView mLvCourse = searchableView.findViewById(R.id.lv_course);
                final SearchView mSvCourse = searchableView.findViewById(R.id.sv_course);

                // Set MyAdapter on the ListView containing all ANU courses
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
                        } else {
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
                        if (lectureList.size() != 0) {
                            StringBuilder builder = new StringBuilder();
                            for (String s : lectureList) {
                                builder.append(s + "\n");
                            }
                            mTvLectureDetails.setText(builder.toString().substring(0, builder.length() - 1));
                        } else {
                            mTvLectureDetails.setText("There is no scheduled lecture for this course.");
                        }
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(searchableView);
                dialog.show();
                // If the courseID is changed, delete all tutorials in the tutorial ListView.
                if (tutorialList != null) tutorialList.clear();
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

        // Click the add button to go to the add page
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(EnrolActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        // Click the delete button to delete enrolled courses from the list and the calender
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
                            if (mCtv.isChecked()) {
                                String temp = mCtv.getText().toString();
                                // Delete selected courses from user.json
                                user.delete(temp.substring(0, 11));
                                enrolAdapter.remove(temp);
                            }
                        }
                        // Uncheck the boxes that have been checked
                        SparseBooleanArray tmp = mLvEnrolledCourses.getCheckedItemPositions();
                        for (int k = 0; k < tmp.size(); k++) {
                            mLvEnrolledCourses.setItemChecked(k, false);
                        }
                        enrolAdapter.notifyDataSetChanged();
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

        // Set the enrol button to check conflicts, and if there is no conflict, the course will be
        // add to the list and the calender
        mBtnEnrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EnrolActivity.this);
                alertDialog.setTitle("Enrol");
                alertDialog.setIcon(R.drawable.icon_enrol_course);
                alertDialog.setMessage("Do you really want to enrol this course?");
                alertDialog.setCancelable(true);
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<String> newEnrolCourses = new ArrayList<>();
                        String selectedcourseID = mTvCourseID.getText().toString();
                        String[] selectedCourseDetails = mTvLectureDetails.getText().toString().split("\n");
                        for (String s : selectedCourseDetails) {
                            newEnrolCourses.add(s.substring(0, 7));
                        }
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
                        Map<String, List<String>> temp = new HashMap<>();
                        temp.put(selectedcourseID, newEnrolCourses);
                        // Add the selected course to userCourseList
                        Map<String, String> temp2 = user.save(temp);
                        if (temp2.get("status").equals("false")) {
                            enrolledCourses.add(selectedcourseID);
                            enrolAdapter.add(selectedcourseID);
                            enrolAdapter.notifyDataSetChanged();
                            // Make a custom toast
                            MyToast myToast = MyToast.makeText(view.getContext(), temp2.get("message"), Toast.LENGTH_LONG);
                            myToast.setGravity(Gravity.CENTER, 0, 0);
                            myToast.show();
                        } else {
                            Toast.makeText(view.getContext(), temp2.get("message"), Toast.LENGTH_SHORT).show();
                            // Return to the main activity
                            finish();
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
    }
}
