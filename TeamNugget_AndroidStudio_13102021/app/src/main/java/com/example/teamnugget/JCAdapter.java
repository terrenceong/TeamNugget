package com.example.teamnugget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JCAdapter extends RecyclerView.Adapter<JCAdapter.ViewHolder> {
    Context c;
    List<Institute> jcList;

    public JCAdapter(Context c, List<Institute> jcList) {
        this.c = c;
        this.jcList = jcList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.item_layout_jc,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(jcList!=null && jcList.size() >0)
        {
            Institute jc = jcList.get(position);
            holder.jcname.setText(jc.getName());
        }
        else return;
    }

    @Override
    public int getItemCount() {
        return jcList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView jcname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            jcname = itemView.findViewById(R.id.jcname);
        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            Intent i = new Intent(c,JCHome.class);
            i.putExtra("jcPosition", position);
            c.startActivity(i);
        }
    }
}

