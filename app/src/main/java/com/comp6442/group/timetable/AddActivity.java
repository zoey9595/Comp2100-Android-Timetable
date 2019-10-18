
package com.comp6442.group.timetable;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AddActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    //declare all the variables
    private EditText mEditCID, mEditCName;
    private Spinner mSpSemester;
    private Button mBtnFind, mBtnAdd, mBtnSave, mBtnDelete;
    private ListView mListViewDetail;
    private LinearLayout mlladd1,mlladd2,mlladd3;
    private int index = 0, screenHight;
    private String mCourseID,mCourseName, duration;
    private Course mCourse = Course.getCourseInstance(AddActivity.this);
    private CourseDetailAdapter mCourseDetailAdapter;
    private ArrayList<CourseDetailInfo> mCourseDetailInfos = new ArrayList<>();
    private ArrayList<Map<String,String>> mCourseDetails;
    private Map<String,String> courseMap = new HashMap<>();

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
        mBtnSave = findViewById(R.id.btn_add_save);
        mBtnDelete = findViewById(R.id.btn_add_delete);
        mListViewDetail = findViewById(R.id.lv_add_detail);
        mlladd1 = findViewById(R.id.ll_add1);
        mlladd2 = findViewById(R.id.ll_add2);
        mlladd3 = findViewById(R.id.ll_add3);

        //Set widgets fit for different screen sizes
        DisplayMetrics dmAdd = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dmAdd);
        screenHight = dmAdd.heightPixels;

        RelativeLayout.LayoutParams mLladd1Params = (RelativeLayout.LayoutParams) mlladd1.getLayoutParams();
        mLladd1Params.height = screenHight / 20;
        mlladd1.setLayoutParams(mLladd1Params);


        RelativeLayout.LayoutParams mLladd2Params = (RelativeLayout.LayoutParams) mlladd2.getLayoutParams();
        mLladd2Params.height = screenHight / 20;
        mlladd2.setLayoutParams(mLladd2Params);

        RelativeLayout.LayoutParams mLladd3Params = (RelativeLayout.LayoutParams) mlladd3.getLayoutParams();
        mLladd3Params.height = screenHight / 18;
        mlladd3.setLayoutParams(mLladd3Params);

        RelativeLayout.LayoutParams mLvCourseDetailLayoutParams = (RelativeLayout.LayoutParams) mListViewDetail.getLayoutParams();
        mLvCourseDetailLayoutParams.height = screenHight / 2;
        mListViewDetail.setLayoutParams(mLvCourseDetailLayoutParams);


        //set listeners and adapter
        ArrayAdapter<CharSequence> adapterSemester = ArrayAdapter.createFromResource(AddActivity.this, R.array.semester,android.R.layout.simple_spinner_item);
        adapterSemester.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpSemester.setAdapter(adapterSemester);
        mBtnFind.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        mListViewDetail.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_findCourse:
                mCourseDetailInfos.clear();
                mCourseID = mEditCID.getText().toString().toUpperCase()+"_"+mSpSemester.getSelectedItem().toString();
                mCourseName = mCourse.getCourseName(mCourseID);
                mEditCName.setText(mCourseName);
                index = mCourse.getLecDetailsInsplit(mCourseID).size();
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

                if (mCourseName.length()==0) {
                    Toast.makeText(AddActivity.this, "Please add the course information by yourself", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btn_addclass:
                mCourseDetailAdapter = new CourseDetailAdapter(AddActivity.this,mCourseDetailInfos);
                mCourseDetailInfos.add(index,new CourseDetailInfo("","","","","",""));
                mListViewDetail.setAdapter(mCourseDetailAdapter);
                index++;
                break;

            case R.id.btn_add_save:
                boolean flag = false;
                for (int i=0;i<mListViewDetail.getCount();i++){
                    if (mCourseDetailInfos.get(i).getLessonType().length()==0||mCourseDetailInfos.get(i).getLessonAlph().length()==0
                            ||mCourseDetailInfos.get(i).getLessonNum().length()==0||mCourseDetailInfos.get(i).getLessonDay().length()==0
                            ||mCourseDetailInfos.get(i).getLessonStart().length()==0||mCourseDetailInfos.get(i).getLessonEnd().length()==0){
                        flag = true;
                    }
                    else flag = false;
                }

                boolean timeFlag = false;
                for (int i=0;i<mListViewDetail.getCount();i++){
                    if (mCourseDetailInfos.get(i).getLessonStartInt() >= mCourseDetailInfos.get(i).getLessonEndInt() ){
                        timeFlag = true;
                    }
                    else timeFlag = false;
                }


                if (mEditCID.getText().length()== 0 || mEditCName.getText().length() == 0||flag||timeFlag){
                    Toast.makeText(AddActivity.this, "You can not save, please edit it again", Toast.LENGTH_SHORT).show();
                }

                else
                    { new AlertDialog.Builder(AddActivity.this)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to refresh all the information about this course?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCourseID = mEditCID.getText().toString()+"_"+mSpSemester.getSelectedItem().toString();
                                mCourseName = mEditCName.getText().toString();
                                int size = mListViewDetail.getCount();
                                mCourseDetails = new ArrayList<>();
                                for (int i=0;i<size;i++){
                                    int durationInt = (mCourseDetailInfos.get(i).getLessonEndInt()-mCourseDetailInfos.get(i).getLessonStartInt());
                                    if (durationInt%2==0){
                                        duration = Integer.toString((durationInt/2))+":00";
                                    }else duration = Integer.toString(durationInt/2)+":30";
                                    String lessonName = mCourseDetailInfos.get(i).getLessonType()+mCourseDetailInfos.get(i).getLessonAlph()+"/"+mCourseDetailInfos.get(i).getLessonNum();
                                    courseMap = new HashMap<>();
                                    courseMap.put("id",mEditCID.getText().toString());
                                    courseMap.put("semester", mSpSemester.getSelectedItem().toString());
                                    courseMap.put("courseName",mEditCName.getText().toString());
                                    courseMap.put("name", lessonName);
                                    courseMap.put("weekday", mCourseDetailInfos.get(i).getLessonDay());
                                    courseMap.put("start", mCourseDetailInfos.get(i).getLessonStart());
                                    courseMap.put("end",mCourseDetailInfos.get(i).getLessonEnd());
                                    courseMap.put("duration", duration);
                                    mCourseDetails.add(courseMap);
                                }
                                    mCourse.save(mCourseDetails);
                                    if (mCourse.save(mCourseDetails).get("status")=="true"){
                                        Toast.makeText(AddActivity.this, mCourse.save(mCourseDetails).get("message"), Toast.LENGTH_LONG).show();
                                        }
                                    if (mCourse.save(mCourseDetails).get("status")=="false"){
                                        Toast.makeText(AddActivity.this, mCourse.save(mCourseDetails).get("message"), Toast.LENGTH_LONG).show();
                                    }
                                    mCourseDetailAdapter.refreshData(mCourseDetailInfos);
                                    finish();
                            }
                        })
                        .setNegativeButton("No",null).show();
                }


                break;
            case R.id.btn_add_delete:
                new AlertDialog.Builder(AddActivity.this)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete all the information about this course?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCourseID = mEditCID.getText().toString()+"_"+mSpSemester.getSelectedItem().toString();
                                mCourse.delete(mCourseID);
                                if (mCourse.delete(mCourseID).get("status")=="true"){
                                    Toast.makeText(AddActivity.this, mCourse.delete(mCourseID).get("message"), Toast.LENGTH_LONG).show();
                                }
                                if (mCourse.delete(mCourseID).get("status")=="false"){
                                    Toast.makeText(AddActivity.this, mCourse.delete(mCourseID).get("message"), Toast.LENGTH_LONG).show();
                                }
                                mCourseDetailInfos.clear();
                                mCourseDetailAdapter.refreshData(mCourseDetailInfos);
                            }
                        })
                        .setNegativeButton("No",null).show();
                break;
        }

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
                        finish();
                    }
                })
                .setNegativeButton("No",null).show();
        return true;
    }

}

