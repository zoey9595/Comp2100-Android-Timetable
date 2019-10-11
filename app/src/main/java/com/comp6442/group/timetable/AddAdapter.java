package com.comp6442.group.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Map;

public class AddAdapter extends BaseAdapter {

    private Context mContext;
    private Course course;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Map<String,String>> courseDetails;
    private Map<String,String> element = null;
    private String courseID;

    public AddAdapter(Context context,Course course, ArrayList<Map<String,String>> courseDetails){
        super();
        this.mContext = context;
        this.course = course;
        this.courseDetails = courseDetails;
        mLayoutInflater = LayoutInflater.from(context);
    }

    // length of list
    @Override
    public int getCount() {
        return courseDetails.size();
    }

    @Override
    public Object getItem(int position) {
        if (courseDetails.size() == 0) return null;
        else return courseDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        Button btnDel;
        Spinner spStart,spEnd,spDay,spType,spAlph,spNum;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView==null){
            convertView = mLayoutInflater.inflate(R.layout.layout_list_course_detail,null);

            holder = new ViewHolder();

            holder.btnDel = convertView.findViewById(R.id.btn_del);
            holder.spStart = convertView.findViewById(R.id.spinner_start);
            holder.spEnd = convertView.findViewById(R.id.spinner_end);
            holder.spDay = convertView.findViewById(R.id.spinner_day);
            holder.spType = convertView.findViewById(R.id.spinner_CType);
            holder.spAlph = convertView.findViewById(R.id.spinner_CAlph);
            holder.spNum = convertView.findViewById(R.id.spinner_CNum);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //Add values to spinners
        // spinner of the day
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(mContext,R.array.day,android.R.layout.simple_spinner_item);
        holder.spDay.setAdapter(adapter1);
        int posDay = adapter1.getPosition(courseDetails.get(position).get(Utility.WEEKDAY));
        holder.spDay.setSelection(posDay,true);

        //spinner of the start time
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(mContext,R.array.start,android.R.layout.simple_spinner_item);
        holder.spStart.setAdapter(adapter2);
        int posStart = adapter2.getPosition(courseDetails.get(position).get(Utility.START));
        holder.spStart.setSelection(posStart,true);

        //spinner of the end time
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(mContext,R.array.end,android.R.layout.simple_spinner_item);
        holder.spEnd.setAdapter(adapter3);
        int posEnd = adapter3.getPosition(courseDetails.get(position).get(Utility.END));
        holder.spEnd.setSelection(posEnd,true);

        //spinner of the class type
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(mContext,R.array.classType,android.R.layout.simple_spinner_item);
        holder.spType.setAdapter(adapter4);
        int posType = adapter4.getPosition(courseDetails.get(position).get(Utility.NAME_TYPE));
        holder.spType.setSelection(posType,true);

        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(mContext,R.array.classAph,android.R.layout.simple_spinner_item);
        holder.spAlph.setAdapter(adapter5);
        int posAlph = adapter5.getPosition(courseDetails.get(position).get(Utility.NAME_ALP));
        holder.spAlph.setSelection(posAlph,true);

        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(mContext,R.array.classNum,android.R.layout.simple_spinner_item);
        holder.spNum.setAdapter(adapter6);
        int posNum= adapter6.getPosition(courseDetails.get(position).get(Utility.NAME_INDEX));
        holder.spNum.setSelection(posNum,true);

        return convertView;
    }


    public void addItem(int position, Map<String,String> element){
        if (courseDetails == null){
            courseDetails = new ArrayList<>();
        }
        courseDetails.add(position, element);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        if (courseDetails != null){
            courseDetails.remove(position);
        }
        notifyDataSetChanged();
    }


}
