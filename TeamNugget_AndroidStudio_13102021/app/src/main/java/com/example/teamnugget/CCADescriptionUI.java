package com.example.teamnugget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CCADescriptionUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String description = getIntent().getStringExtra("description");
        String ccaname = getIntent().getStringExtra("ccaname");
        setContentView(R.layout.activity_ccadescription_ui);
        TextView name = findViewById(R.id.tv_cc_name);
        TextView descrip = findViewById(R.id.tv_cc_description);
        name.setText(ccaname);
        descrip.setText(description);


    }
}