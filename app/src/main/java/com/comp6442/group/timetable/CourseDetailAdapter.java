package com.comp6442.group.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseDetailAdapter extends BaseAdapter  {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Map<String,String>> mCourseDetails;

    private List<CourseDetailInfo> mCourseDetailInfos;


    public CourseDetailAdapter(Context context, List<CourseDetailInfo> courseDetailInfos) {
        super();
        mContext = context;
        //mCourseDetails = courseDetails;
        mCourseDetailInfos = courseDetailInfos;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mCourseDetailInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mCourseDetailInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewerHolder viewerHolder;

        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.layout_list_course_detail,null);

            viewerHolder = new ViewerHolder();

            //get widgets
            viewerHolder.spType = convertView.findViewById(R.id.spinner_CType);
            viewerHolder.spAlph = convertView.findViewById(R.id.spinner_CAlph);
            viewerHolder.spNum = convertView.findViewById(R.id.spinner_CNum);
            viewerHolder.spDay = convertView.findViewById(R.id.spinner_day);
            viewerHolder.spStart = convertView.findViewById(R.id.spinner_start);
            viewerHolder.spEnd = convertView.findViewById(R.id.spinner_end);
            viewerHolder.btnLesson = convertView.findViewById(R.id.btn_editclass);

            convertView.setTag(viewerHolder);
        }else{

            viewerHolder = (ViewerHolder) convertView.getTag();

        }

        // spinner of the day
        setSpinner(position, viewerHolder);

        //监听





        return convertView;
    }
    public int itemPos ,tempType, tempAlph,tempNum,tempDay,tempStart,tempEnd;
    private void setSpinner(int position, final ViewerHolder viewerHolder) {
        itemPos = position;

        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(mContext,R.array.day,android.R.layout.simple_spinner_item);
        viewerHolder.spDay.setAdapter(adapter1);
        int posDay = adapter1.getPosition(mCourseDetailInfos.get(position).getLessonDay());
        viewerHolder.spDay.setSelection(posDay,true);

        //spinner of the start time
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(mContext,R.array.start,android.R.layout.simple_spinner_item);
        viewerHolder.spStart.setAdapter(adapter2);
        int posStart = adapter2.getPosition(mCourseDetailInfos.get(position).getLessonStart());
        viewerHolder.spStart.setSelection(posStart,true);

        //spinner of the end time
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(mContext,R.array.end,android.R.layout.simple_spinner_item);
        viewerHolder.spEnd.setAdapter(adapter3);
        int posEnd = adapter3.getPosition(mCourseDetailInfos.get(position).getLessonEnd());
        viewerHolder.spEnd.setSelection(posEnd,true);

        //spinner of the class type
        final ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(mContext,R.array.classType,android.R.layout.simple_spinner_item);
        viewerHolder.spType.setAdapter(adapter4);
        int posType = adapter4.getPosition(mCourseDetailInfos.get(position).getLessonType());
        viewerHolder.spType.setSelection(posType,true);

        final ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(mContext,R.array.classAph,android.R.layout.simple_spinner_item);
        viewerHolder.spAlph.setAdapter(adapter5);
        int posAlph = adapter5.getPosition(mCourseDetailInfos.get(position).getLessonAlph());
        viewerHolder.spAlph.setSelection(posAlph,true);

        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(mContext,R.array.classNum,android.R.layout.simple_spinner_item);
        viewerHolder.spNum.setAdapter(adapter6);
        adapter6.notifyDataSetChanged();
        int posNum= adapter6.getPosition(mCourseDetailInfos.get(position).getLessonNum());
        viewerHolder.spNum.setSelection(posNum,true);

        //set listeners
        viewerHolder.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                tempType = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        viewerHolder.spAlph.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                tempAlph = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        viewerHolder.spNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                tempNum = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        viewerHolder.spDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                tempDay = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        viewerHolder.spStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                tempStart = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        viewerHolder.spEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                tempEnd = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


        viewerHolder.btnLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(itemPos);
                System.out.println(viewerHolder.spType.getSelectedItem().toString());
                mCourseDetailInfos.get(0).setLessonType(viewerHolder.spType.getSelectedItem().toString());
                viewerHolder.spType.setSelection(tempType,true);

                viewerHolder.spAlph.setSelection(tempAlph);
                System.out.println(viewerHolder.spAlph.getSelectedItem().toString());
                mCourseDetailInfos.get(itemPos).setLessonAlph(viewerHolder.spAlph.getSelectedItem().toString());

                viewerHolder.spNum.setSelection(tempNum);
                System.out.println(viewerHolder.spNum.getSelectedItem().toString());
                mCourseDetailInfos.get(itemPos).setLessonNum(viewerHolder.spNum.getSelectedItem().toString());

                viewerHolder.spDay.setSelection(tempDay);
                System.out.println(viewerHolder.spDay.getSelectedItem().toString());
                mCourseDetailInfos.get(itemPos).setLessonDay(viewerHolder.spDay.getSelectedItem().toString());

                viewerHolder.spStart.setSelection(tempStart);
                System.out.println(viewerHolder.spStart.getSelectedItem().toString());
                mCourseDetailInfos.get(itemPos).setLessonStart(viewerHolder.spStart.getSelectedItem().toString());

                viewerHolder.spEnd.setSelection(tempEnd);
                System.out.println(viewerHolder.spEnd.getSelectedItem().toString());
                mCourseDetailInfos.get(itemPos).setLessonAlph(viewerHolder.spEnd.getSelectedItem().toString());
            }
        });

    }




    class ViewerHolder{
        Spinner spStart, spEnd, spDay, spType, spAlph, spNum;
        Button btnLesson;
    }

    //update information
    public void refreshData(List<CourseDetailInfo> courseDetailInfos){
        mCourseDetailInfos = courseDetailInfos;
        notifyDataSetChanged();
    }

    public void addData (CourseDetailInfo courseDetailInfos){
        if (mCourseDetailInfos==null){
            mCourseDetailInfos = new ArrayList<>();
        }
        mCourseDetailInfos.add(courseDetailInfos);
        notifyDataSetChanged();
    }

}
