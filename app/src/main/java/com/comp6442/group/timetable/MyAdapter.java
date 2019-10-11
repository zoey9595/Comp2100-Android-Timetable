/**
 * Author: Yuqing Zhai
 * UID: u6865190
 * MyAdapter is a custom adapter for ListView to make it searchable.
 */
package com.comp6442.group.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter implements Filterable {
    public Context context;
    public ArrayList<String> courseidlist;
    public ArrayList<String> orig;
    public Course course;

    public MyAdapter(Context context, Course course, ArrayList<String> courseidlist) {
        super();
        this.course = course;
        this.context = context;
        this.courseidlist = courseidlist;
    }

    public class MyCourseHolder {
        TextView courseID;
        TextView courseName;
    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<String> results = new ArrayList<>();
                if (orig == null)
                    orig = courseidlist;
                if (charSequence != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final String g : orig) {
                            if (g.contains(charSequence.toString()) ||
                                    g.toLowerCase().contains(charSequence.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence,
                                          FilterResults filterResults) {
                courseidlist = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return courseidlist.size();
    }

    @Override
    public Object getItem(int i) {
        return courseidlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyCourseHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_two_lines_listview, viewGroup, false);
            holder = new MyCourseHolder();
            holder.courseID = view.findViewById(R.id.text1);
            holder.courseName = view.findViewById(R.id.text2);
            view.setTag(holder);
        } else {
            holder = (MyCourseHolder) view.getTag();
        }

        holder.courseID.setText(courseidlist.get(i));
        holder.courseName.setText(course.getCourseName(courseidlist.get(i)));

        return view;
    }

}
