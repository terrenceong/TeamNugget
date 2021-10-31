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
    List<String> alevel;
    List<String> gpa;
    List<String> name;
    public IntakeReqAdapater(Context context, List<String> alevel,List<String> gpa,List<String> name) {
        this.context = context;
        this.alevel = alevel;
        this.gpa = gpa;
        this.name = name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout2,parent,false);

        return new IntakeReqAdapater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            if(name.size()!=0 && alevel.size()!=0 && gpa.size()!=0)
            {
                holder.programmeName.setText(name.get(position));
                holder.alevel.setText(alevel.get(position));
                holder.gpa.setText(gpa.get(position));
            }


    }

    @Override
    public int getItemCount() {
        return alevel.size();
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
