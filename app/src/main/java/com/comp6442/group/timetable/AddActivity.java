package com.comp6442.group.timetable;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;



public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //declare all the variables of widgets
    public static List<Map<String, String>> classDetails;
    private Spinner spinner_semester;
    private Button btn_find;
    private EditText edit_CName;
    private EditText edit_CID;
    private ListView mLvCDetail;
    private List<Map<String, String>> classDetailList;
    private String courseName, courseID;
    final Course course = Course.getCourseInstance(this);


    // This is a method that init all the widget
    private void bindViews() {

        //find all the widgets;
        spinner_semester = findViewById(R.id.spinner_semester);
        edit_CName = findViewById(R.id.edit_cname);
        edit_CID = findViewById(R.id.edit_cid);
        btn_find = findViewById(R.id.btn_findCourse);
        mLvCDetail = findViewById(R.id.lv_c_detail);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Add Course");

        bindViews();

        //button setting
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //search course ID and add course name if the ID is correct
                courseID = edit_CID.getText().toString() + "_" + spinner_semester.getSelectedItem().toString();
                courseName = course.getCourseName(courseID);
                if (courseName.length() == 0) {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Please Add Course Information", Toast.LENGTH_LONG);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                } else {
                    edit_CName.setText(courseName);
                }
                //search course ID and add details in the list if it is correct
                classDetailList = course.getLecDetailsInsplit(courseID);

                //adapter
                AddAdapter addAdapter = new AddAdapter(v.getContext(), course, classDetailList);
                mLvCDetail.setAdapter(addAdapter);


            }
        });

        //spinner that choose semester or spring/summer/autumn/winter section of the courses
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.semester, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_semester.setAdapter(adapter1);
        spinner_semester.setOnItemSelectedListener(this);



        //改变coursename


    }


    // set spinner to choose course semester
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView)parent.getChildAt(0)).setTextColor(getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

}
