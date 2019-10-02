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

    private Spinner spinner_semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Add Course");

        //spinner that choose semester or spring/summer/autumn/winter section of the courses
        spinner_semester = findViewById(R.id.spinner_semester);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.semester, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_semester.setAdapter(adapter);
        spinner_semester.setOnItemSelectedListener(this);

    }

    public void saveButton(View view) {
    }

    public void addDetail(View view) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView)parent.getChildAt(0)).setTextSize(20);
        ((TextView)parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
