package com.comp6442.group.timetable;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //declare all the variables of widgets
    private Spinner spinner_semester;
    private Spinner spinner_day;
    private Spinner spinner_class1;
    private Spinner spinner_class2;
    private Spinner spinner_class3;
    private Spinner spinner_statime;
    private Spinner spinner_endtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Add Course");

        //initControl();

        //spinner that choose semester or spring/summer/autumn/winter section of the courses
        spinner_semester = findViewById(R.id.spinner_semester);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.semester, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_semester.setAdapter(adapter1);
        spinner_semester.setOnItemSelectedListener(this);

        //spinner that choose day
        spinner_day = findViewById(R.id.spinner_day);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_day.setAdapter(adapter2);
        spinner_day.setOnItemSelectedListener(this);

        //*******************一行的布局*****************
        spinner_class1 = findViewById(R.id.spinner_1);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.class1, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_class1.setAdapter(adapter4);
        spinner_class1.setOnItemSelectedListener(this);

        spinner_class2 = findViewById(R.id.spinner_2);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this, R.array.class2, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_class2.setAdapter(adapter5);
        spinner_class2.setOnItemSelectedListener(this);

        spinner_class3 = findViewById(R.id.spinner_3);
        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this, R.array.class3, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_class3.setAdapter(adapter6);
        spinner_class3.setOnItemSelectedListener(this);

        spinner_day = findViewById(R.id.spinner_4);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.day, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_day.setAdapter(adapter3);
        spinner_day.setOnItemSelectedListener(this);

        spinner_statime = findViewById(R.id.spinner_5);
        ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(this, R.array.start, android.R.layout.simple_spinner_item);
        adapter7.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_statime.setAdapter(adapter7);
        spinner_statime.setOnItemSelectedListener(this);

        spinner_endtime = findViewById(R.id.spinner_6);
        ArrayAdapter<CharSequence> adapter8 = ArrayAdapter.createFromResource(this, R.array.end, android.R.layout.simple_spinner_item);
        adapter8.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_endtime.setAdapter(adapter8);
        spinner_endtime.setOnItemSelectedListener(this);

    }

    /* This is a method that init all the widget
    * */
    private void initControl(){
        //find all the widgets;


    }

    public void saveButton(View view) {
    }

    public void cancelButton(View view) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView)parent.getChildAt(0)).setTextSize(15);
        ((TextView)parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
