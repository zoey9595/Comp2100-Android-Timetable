package com.comp6442.group.timetable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

    private List<CourseDetailInfo> mCourseDetailInfolist;

    private final String TAG = "detaillist";


    public CourseDetailAdapter(Context context, List<CourseDetailInfo> courseDetailInfos) {
        super();
        mContext = context;
        mCourseDetailInfolist = courseDetailInfos;

    }


    @Override
    public int getCount() {
        return mCourseDetailInfolist.size();
    }

    @Override
    public Object getItem(int position) {
        return null != mCourseDetailInfolist? mCourseDetailInfolist.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewerHolder{
        Spinner spStart, spEnd, spDay, spType, spAlph, spNum;
        Button btnLesson;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         ViewerHolder viewerHolder = null;
         CourseDetailInfo courseDetailInfoType = (CourseDetailInfo) getItem(position);
         CourseDetailInfo courseDetailInfoAlph = (CourseDetailInfo) getItem(position);
         CourseDetailInfo courseDetailInfoNum = (CourseDetailInfo) getItem(position);
         CourseDetailInfo courseDetailInfoDay = (CourseDetailInfo) getItem(position);
         CourseDetailInfo courseDetailInfoStart = (CourseDetailInfo) getItem(position);
         CourseDetailInfo courseDetailInfoEnd = (CourseDetailInfo) getItem(position);

        if (convertView == null){
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

            final ViewerHolder fianlViewerHolder = viewerHolder;

            viewerHolder.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CourseDetailInfo infoType = (CourseDetailInfo)fianlViewerHolder.spType.getTag(R.id.spinner_CType);
                    infoType.setLessonType(parent.getSelectedItem().toString());
                }@Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });

            viewerHolder.spAlph.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CourseDetailInfo infoAlph = (CourseDetailInfo)fianlViewerHolder.spAlph.getTag(R.id.spinner_CAlph);
                    infoAlph.setLessonAlph(parent.getSelectedItem().toString());
                }@Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });

            viewerHolder.spNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CourseDetailInfo infoNum = (CourseDetailInfo)fianlViewerHolder.spNum.getTag(R.id.spinner_CNum);
                    infoNum.setLessonNum(parent.getSelectedItem().toString());
                }@Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });

            viewerHolder.spDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CourseDetailInfo infoDay = (CourseDetailInfo)fianlViewerHolder.spDay.getTag(R.id.spinner_day);
                    infoDay.setLessonDay(parent.getSelectedItem().toString());
                }@Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });

            viewerHolder.spStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CourseDetailInfo infoStart = (CourseDetailInfo)fianlViewerHolder.spStart.getTag(R.id.spinner_start);
                    infoStart.setLessonStart(parent.getSelectedItem().toString());
                }@Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });

            viewerHolder.spEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CourseDetailInfo infoEnd = (CourseDetailInfo)fianlViewerHolder.spEnd.getTag(R.id.spinner_end);
                    infoEnd.setLessonEnd(parent.getSelectedItem().toString());
                }@Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });

            convertView.setTag(viewerHolder);
            viewerHolder.spType.setTag(R.id.spinner_CType,courseDetailInfoType);
            viewerHolder.spAlph.setTag(R.id.spinner_CAlph,courseDetailInfoAlph);
            viewerHolder.spNum.setTag(R.id.spinner_CNum,courseDetailInfoNum);
            viewerHolder.spDay.setTag(R.id.spinner_day,courseDetailInfoDay);
            viewerHolder.spStart.setTag(R.id.spinner_start,courseDetailInfoStart);
            viewerHolder.spEnd.setTag(R.id.spinner_end,courseDetailInfoEnd);
        }else{

            viewerHolder = (ViewerHolder) convertView.getTag();
            viewerHolder.spType.setTag(R.id.spinner_CType,courseDetailInfoType);
            viewerHolder.spAlph.setTag(R.id.spinner_CAlph,courseDetailInfoAlph);
            viewerHolder.spNum.setTag(R.id.spinner_CNum,courseDetailInfoNum);
            viewerHolder.spDay.setTag(R.id.spinner_day,courseDetailInfoDay);
            viewerHolder.spStart.setTag(R.id.spinner_start,courseDetailInfoStart);
            viewerHolder.spEnd.setTag(R.id.spinner_end,courseDetailInfoEnd);
        }
        // set all the spinners
        setSpinner(position, viewerHolder);

        return convertView;
    }

    private void setSpinner(int position, final ViewerHolder viewerHolder) {
        //spinner of the day
        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(mContext,R.array.day,android.R.layout.simple_spinner_item);
        viewerHolder.spDay.setAdapter(adapter1);
        int posDay = adapter1.getPosition(mCourseDetailInfolist.get(position).getLessonDay());
        viewerHolder.spDay.setSelection(posDay,true);

        //spinner of the start time
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(mContext,R.array.start,android.R.layout.simple_spinner_item);
        viewerHolder.spStart.setAdapter(adapter2);
        int posStart = adapter2.getPosition(mCourseDetailInfolist.get(position).getLessonStart());
        viewerHolder.spStart.setSelection(posStart,true);

        //spinner of the end time
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(mContext,R.array.end,android.R.layout.simple_spinner_item);
        viewerHolder.spEnd.setAdapter(adapter3);
        int posEnd = adapter3.getPosition(mCourseDetailInfolist.get(position).getLessonEnd());
        viewerHolder.spEnd.setSelection(posEnd,true);

        //spinner of the class type
        final ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(mContext,R.array.classType,android.R.layout.simple_spinner_item);
        viewerHolder.spType.setAdapter(adapter4);
        int posType = adapter4.getPosition(mCourseDetailInfolist.get(position).getLessonType());
        viewerHolder.spType.setSelection(posType,true);

        final ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(mContext,R.array.classAph,android.R.layout.simple_spinner_item);
        viewerHolder.spAlph.setAdapter(adapter5);
        int posAlph = adapter5.getPosition(mCourseDetailInfolist.get(position).getLessonAlph());
        viewerHolder.spAlph.setSelection(posAlph,true);

        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(mContext,R.array.classNum,android.R.layout.simple_spinner_item);
        viewerHolder.spNum.setAdapter(adapter6);
        adapter6.notifyDataSetChanged();
        int posNum= adapter6.getPosition(mCourseDetailInfolist.get(position).getLessonNum());
        viewerHolder.spNum.setSelection(posNum,true);
    }

    //update information in the courseDetailInfo list
    public void refreshData(List<CourseDetailInfo> courseDetailInfos){
        mCourseDetailInfolist = courseDetailInfos;
        notifyDataSetChanged();
    }

    public void addData (CourseDetailInfo courseDetailInfos){
        if (mCourseDetailInfolist==null){
            mCourseDetailInfolist = new ArrayList<>();
        }
        mCourseDetailInfolist.add(courseDetailInfos);
        notifyDataSetChanged();
    }
}
