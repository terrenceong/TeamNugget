package com.example.teamnugget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IntakeReqAdapater extends RecyclerView.Adapter<IntakeReqAdapater.ViewHolder> {

    Context context;
    List<UniversityCourse> courseList;
    public IntakeReqAdapater(Context context, List<UniversityCourse> courseslist) {
        this.context = context;
        this.courseList = courseslist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout2,parent,false);

        return new IntakeReqAdapater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if(courseList.size() > 0 && courseList!=null)
            {
                holder.programmeName.setText(courseList.get(position).name);
                holder.alevel.setText(courseList.get(position).getCutOffPointsAL());
                holder.gpa.setText(courseList.get(position).getCutOffPointsGPA()+"");
            }
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView programmeName,gpa,alevel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            programmeName = itemView.findViewById(R.id.programmeName);
            gpa = itemView.findViewById(R.id.gpa);
            alevel = itemView.findViewById(R.id.alevel);
        }


    }
}
