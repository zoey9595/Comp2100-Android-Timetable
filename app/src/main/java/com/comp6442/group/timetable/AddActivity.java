package com.comp6442.group.timetable;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.comp6442.group.timetable.Utility.NAME_INDEX;
import static com.comp6442.group.timetable.Utility.NAME_TYPE;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //declare all the variables of widgets
    private Spinner spinner_semester,spinner_day;
    private Button btn_find;
    private EditText edit_CName;
    private EditText edit_CID;
    private ListView mLvCDetail;
    private List<Map<String,String>> classDetailList;
    private String courseName, courseID;


    /* This is a method that init all the widget
     * */
    private void initControl(){
        //find all the widgets;
        spinner_semester = findViewById(R.id.spinner_semester);
        spinner_day = findViewById(R.id.spinner_day);
        edit_CName = findViewById(R.id.edit_cname);
        edit_CID = findViewById(R.id.edit_cid);
        btn_find = findViewById(R.id.btn_findCourse);
        mLvCDetail = findViewById(R.id.lv_c_detail);


        //
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Add Course");

        initControl();

        final Course course = Course.getCourseInstance(this);

        //button setting
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //search course ID and add course name if the ID is correct
                courseID = edit_CID.getText().toString()+"_"+ spinner_semester.getSelectedItem().toString();
                courseName = course.getCourseName(courseID);
                if (courseName.length()==0){
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Please Add Course Information",Toast.LENGTH_LONG);
                    toast1.setGravity(Gravity.CENTER,0,0);
                    toast1.show();
                }else{
                    edit_CName.setText(courseName);
                }

                //search course ID and add details in the list if it is correct
                classDetailList = course.getLecDetailsInsplit(courseID);
                int listSize = classDetailList.size();

                    for (int i=0;i<classDetailList.size();i++){
                        System.out.println(classDetailList.get(i).get(NAME_TYPE)+classDetailList.get(i).get(NAME_INDEX));
                    }






            }

        });

        //spinner that choose semester or spring/summer/autumn/winter section of the courses
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.semester, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_semester.setAdapter(adapter1);
        spinner_semester.setOnItemSelectedListener(this);

        //改变coursename


        //spinner that choose day

//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item);
//        adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        spinner_day.setAdapter(adapter2);
//        spinner_day.setOnItemSelectedListener(this);

        //set listview of course detail

        mLvCDetail.setAdapter(new AddAdapter(AddActivity.this));


    }







    // set spinner to choose course semester
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView)parent.getChildAt(0)).setTextColor(getColor(R.color.colorPrimaryDark));
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}



}
