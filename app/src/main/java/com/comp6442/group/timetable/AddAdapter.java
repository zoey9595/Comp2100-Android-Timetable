package com.comp6442.group.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AddAdapter extends BaseAdapter {

    private Context mContext;
    public Course course;
    private LayoutInflater mLayoutInflater;
    public List<Map<String,String>> classDetails;
    public String courseID;



    public AddAdapter(Context context,Course course, List<Map<String,String>> classDetails){
        super();
        this.mContext = context;
        this.course = course;
        this.classDetails = classDetails;
        mLayoutInflater = LayoutInflater.from(context);
    }




    // length of list
    @Override
    public int getCount() {
        return classDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        public Button btnClass,btnDay,btnDel;
        public Spinner spTime1,spTime2,spDay;
        public TextView tvStart, tvEnd, tvDay;
        public EditText edtClass;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView==null){
            convertView = mLayoutInflater.inflate(R.layout.layout_list_course_detail,null);

            holder = new ViewHolder();


            holder.btnDay = convertView.findViewById(R.id.btn_Day);
            holder.btnClass = convertView.findViewById(R.id.btn_class);
            holder.btnDel = convertView.findViewById(R.id.btn_del_class);
            holder.spTime1 = convertView.findViewById(R.id.spinner_time1);
            holder.spTime2 = convertView.findViewById(R.id.spinner_time2);
            holder.spDay = convertView.findViewById(R.id.spinner_day);
            holder.tvStart = convertView.findViewById(R.id.tv_time1);
            holder.tvEnd = convertView.findViewById(R.id.tv_time2);
            holder.tvDay = convertView.findViewById(R.id.tv_day);
            holder.edtClass = convertView.findViewById(R.id.edit_class);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }




        // add values to weidgts
        holder.edtClass.setText(classDetails.get(position).get(Utility.NAME_TYPE)+classDetails.get(position).get(Utility.NAME_ALP)+"/"+classDetails.get(position).get(Utility.NAME_INDEX));
        holder.edtClass.setTextSize(15);
        holder.tvDay.setText("Day:");
        holder.tvStart.setText("from");
        holder.tvEnd.setText("to");

        return convertView;
    }
}
