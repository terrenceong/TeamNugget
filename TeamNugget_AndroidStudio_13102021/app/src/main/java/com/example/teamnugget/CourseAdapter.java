package com.example.teamnugget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CourseAdapter extends ArrayAdapter<Course> {

    private Context context;
    int resource;

    public CourseAdapter(@NonNull Context context, int resource, @NonNull List<Course> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String courseName = getItem(position).getName();
        String cop = getItem(position).getCutOffPointsGPADisplay();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvName = (TextView)convertView.findViewById(R.id.courseName);
        TextView tvCOP = (TextView)convertView.findViewById(R.id.courseGPA);

        tvName.setText(courseName);
        tvCOP.setText(cop);

        return convertView;
    }
}
