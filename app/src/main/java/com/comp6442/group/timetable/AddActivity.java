
package com.comp6442.group.timetable;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class AddActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemLongClickListener {

    //declare all the variables
    private EditText mEditCID, mEditCName;
    private Spinner mSpSemester;
    private Button mBtnFind, mBtnAdd;
    private ListView mListViewDetail;

    private int index = 0;

    private String mCourseID,mCourseName;
    private Course mCourse = Course.getCourseInstance(AddActivity.this);
    private CourseDetailAdapter mCourseDetailAdapter;
    public ArrayList<CourseDetailInfo> mCourseDetailInfos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Add Course");

        bindViews();

    }

    private void bindViews() {
        mEditCID = findViewById(R.id.edit_cid);
        mEditCName = findViewById(R.id.edit_cname);
        mSpSemester = findViewById(R.id.spinner_semester);
        mBtnFind = findViewById(R.id.btn_findCourse);
        mBtnAdd = findViewById(R.id.btn_addclass);
        mListViewDetail = findViewById(R.id.lv_add_detail);

        ArrayAdapter<CharSequence> adapterSemester = ArrayAdapter.createFromResource(AddActivity.this, R.array.semester,android.R.layout.simple_spinner_item);
        adapterSemester.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);



        mBtnFind.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
        mSpSemester.setAdapter(adapterSemester);
        mSpSemester.setOnItemSelectedListener(this);
        mListViewDetail.setOnItemLongClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_findCourse:
                mCourseID = mEditCID.getText().toString()+"_"+mSpSemester.getSelectedItem().toString();
                mCourseName = mCourse.getCourseName(mCourseID);
                mEditCName.setText(mCourseName);
                index = mCourse.getLecDetailsInsplit(mCourseID).size();
                //mCourseDetailInfos = new ArrayList<>();
                for (int i=0;i<index;i++){
                    String a = mCourse.getLecDetailsInsplit(mCourseID).get(i).get(Utility.NAME_TYPE);
                    String b = mCourse.getLecDetailsInsplit(mCourseID).get(i).get(Utility.NAME_ALP);
                    String c = mCourse.getLecDetailsInsplit(mCourseID).get(i).get(Utility.NAME_INDEX);
                    String d = mCourse.getLecDetailsInsplit(mCourseID).get(i).get(Utility.WEEKDAY);
                    String e = mCourse.getLecDetailsInsplit(mCourseID).get(i).get(Utility.START);
                    String f = mCourse.getLecDetailsInsplit(mCourseID).get(i).get(Utility.END);
                    mCourseDetailInfos.add(new CourseDetailInfo(a,b,c,d,e,f));
                }

                mCourseDetailAdapter = new CourseDetailAdapter(AddActivity.this,mCourseDetailInfos);
                mListViewDetail.setAdapter(mCourseDetailAdapter);
                mCourseDetailAdapter.notifyDataSetChanged();

                if (mCourseName.length()==0){
                    Toast.makeText(AddActivity.this,"Please add the course information by yourself",Toast.LENGTH_LONG).show();
                    }
                break;


            case R.id.btn_addclass:
                mCourseDetailAdapter = new CourseDetailAdapter(AddActivity.this,mCourseDetailInfos);
                mCourseDetailInfos.add(index,new CourseDetailInfo("","","","","",""));
                mCourseDetailAdapter.refreshData(mCourseDetailInfos);
                mListViewDetail.setAdapter(mCourseDetailAdapter);
                index++;
                Log.d("AddActivity","index"+index);
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] semester = getResources().getStringArray(R.array.semester);
        Toast.makeText(AddActivity.this,"You are choosing: "+semester[position],Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final int itemPos = position;
        new AlertDialog.Builder(AddActivity.this)
                .setTitle("Are you sure?")
                .setMessage("Do you want to delete this course detail?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCourseDetailInfos.remove(itemPos);
                        mCourseDetailAdapter.refreshData(mCourseDetailInfos);
                        mListViewDetail.setAdapter(mCourseDetailAdapter);
                        index--;
                    }
                })
                .setNegativeButton("No",null).show();
        return true;
    }




}

