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


public class AddAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public AddAdapter(Context context){
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }



    // length of list
    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{

        public Button btnClass,btnDay,btnAdd,btnDel;
        public Spinner spTime1,spTime2,spDay;
        public TextView tvStart, tvEnd, tvDay;
        public EditText edtClass;
 //       public LinearLayout ll_detailClass, ll_detailDay;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            convertView = mLayoutInflater.inflate(R.layout.layout_list_course_detail,null);

            holder = new ViewHolder();

            holder.btnClass = convertView.findViewById(R.id.btn_del_class);
            holder.btnDay = convertView.findViewById(R.id.btn_Day);
            holder.btnAdd = convertView.findViewById(R.id.btn_add_class);
            holder.btnDel = convertView.findViewById(R.id.btn_del_class);
            holder.spTime1 = convertView.findViewById(R.id.spinner_time1);
            holder.spTime2 = convertView.findViewById(R.id.spinner_time2);
            holder.spDay = convertView.findViewById(R.id.spinner_day);
            holder.tvStart = convertView.findViewById(R.id.tv_time1);
            holder.tvEnd = convertView.findViewById(R.id.tv_time2);
            holder.tvDay = convertView.findViewById(R.id.tv_day);
            holder.edtClass = convertView.findViewById(R.id.edit_class);

//            holder.ll_detailClass = convertView.findViewById(R.id.ll_detail1_class);
//            holder.ll_detailDay = convertView.findViewById(R.id.ll_detail1_day);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        // add values to weidgts
        holder.edtClass.setText("Class Name  e.g.LecA/01");
        holder.edtClass.setTextSize(15);
        holder.tvDay.setText("Day:");
        holder.tvStart.setText("from");
        holder.tvEnd.setText("to");


//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(mContext, R.array.day, android.R.layout.simple_spinner_item);
//        adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        holder.spDay.setAdapter(adapter2);
//        holder.spDay.setOnItemSelectedListener();

        return convertView;
    }
}
