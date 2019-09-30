package com.comp6442.group.timetable;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EnrolActivity extends AppCompatActivity {

    private TextView mTv;
    private Button mBtnEnrol, mBtnCancel, mBtnAdd;
    private ArrayList courseList;
    private Button mSpinnerCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrol);
        setTitle("Enrol");

        mTv = findViewById(R.id.tv_courseID);
        mBtnEnrol = findViewById(R.id.btn_enrol);
        mBtnCancel = findViewById(R.id.btn_cancel);
        mBtnAdd = findViewById(R.id.btn_add);
        mSpinnerCourse = findViewById(R.id.btn_search_course);

        Course course = Course.getCourseInstance(this);
        courseList = (ArrayList) course.getCourseList();

        mSpinnerCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View view1 = inflater.inflate(R.layout.searchable_list_dialog, null);
                final ListView listView = view1.findViewById(R.id.lv_course);
                final SearchView searchView = view1.findViewById(R.id.sv_course);

                final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, courseList);
                listView.setAdapter(stringArrayAdapter);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String newText) {
                        searchView.clearFocus();
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

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        mTv.setText(stringArrayAdapter.getItem(i));
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(view1);
                dialog.show();
            }
        });
    }
}
