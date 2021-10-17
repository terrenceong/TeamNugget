package com.example.teamnugget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CCAAdapter extends RecyclerView.Adapter<CCAAdapter.ViewHolder> {
    Context c;
    List<CCA> ccaList;

    public CCAAdapter(Context c, List<CCA> ccaList) {
        this.c = c;
        this.ccaList = ccaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(ccaList!=null && ccaList.size() >0)
        {
            CCA cca = ccaList.get(position);
            holder.ccaname.setText(cca.getName());
           // holder.ccaDescription.setText(cca.getDescription());

        }
        else return;

    }

    @Override
    public int getItemCount() {
        return ccaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView ccaname;
        //TextView ccaDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ccaname = itemView.findViewById(R.id.ccaname);

        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            Intent i = new Intent(c,CCADescriptionUI.class);
            i.putExtra("description",ccaList.get(position).getDescription());
            i.putExtra("ccaname",ccaList.get(position).getName());
            c.startActivity(i);
            //Toast.makeText(c,"The position is" + position,Toast.LENGTH_SHORT).show();

        }
    }
}

