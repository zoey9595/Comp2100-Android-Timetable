package com.comp6442.group.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseDetailAdapter extends BaseAdapter  {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Map<String,String>> mCourseDetails;

    private List<CourseDetailInfo> mCourseDetailInfos;


    public CourseDetailAdapter(Context context, List<CourseDetailInfo> courseDetailInfos) {

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


            convertView.setTag(viewerHolder);
        }else{

            viewerHolder = (ViewerHolder) convertView.getTag();
        }

        // spinner of the day
        setSpinner(position, viewerHolder);



        return convertView;
    }

    private void setSpinner(int position, final ViewerHolder viewerHolder) {
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
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(mContext,R.array.classType,android.R.layout.simple_spinner_item);
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


//        viewerHolder.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int posT = viewerHolder.spType.getSelectedItemPosition();
//                viewerHolder.spType.setSelection(posT,true);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        viewerHolder.spAlph.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int posA = viewerHolder.spAlph.getSelectedItemPosition();
//                viewerHolder.spAlph.setSelection(posA,true);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) { }
//        });
//
//        viewerHolder.spNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int posN = viewerHolder.spNum.getSelectedItemPosition();
//                viewerHolder.spNum.setSelection(posN,true);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        viewerHolder.spDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int posD = viewerHolder.spDay.getSelectedItemPosition();
//                viewerHolder.spDay.setSelection(posD,true);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        viewerHolder.spStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int posS = viewerHolder.spStart.getSelectedItemPosition();
//                viewerHolder.spStart.setSelection(posS,true);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        viewerHolder.spEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int posE = viewerHolder.spEnd.getSelectedItemPosition();
//                viewerHolder.spEnd.setSelection(posE,true);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }


    class ViewerHolder{
        Spinner spStart, spEnd, spDay, spType, spAlph, spNum;
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
