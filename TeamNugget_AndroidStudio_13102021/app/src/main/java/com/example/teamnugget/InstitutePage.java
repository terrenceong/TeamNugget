package com.example.teamnugget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class InstitutePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyApp","I am here");
        setContentView(R.layout.activity_institute_page);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        //institute is the parameter to determine what information to display here
        String institute = getIntent().getStringExtra("institute");
        TextView header = (TextView) findViewById(R.id.textView);
        //header.setText(institute); // set text.
        // alternatively if u have image do this
        switch(institute)
        {
            case "ITECC":header.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.sg_itec, 0, 0, 0);break;
            case "ITEEAST":header.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.sg_itee, 0, 0, 0);break;
            case "ITEWEST":header.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.sg_itew, 0, 0, 0);break;
            case "NP":break;
        }
        //perform item selectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.filterSearch:
                        startActivity(new Intent(getApplicationContext(),FilterSearch.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),Search.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
        Button b_FnS = findViewById(R.id.facultiesandschool);
        b_FnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),SchoolUI.class);//can add if statement depending on institute type
                i.putExtra("institute", institute);
                startActivity(i);
                switch(institute)
                {
                    /*case "ITECC": ITE cc = new ITE("College Central","ITE College Central " +
                            "embodies creativity and innovation as part of ITE’s unique brand of College Education. " +
                            "The College houses four Schools – School of Business Services, School of Electronics & Info-Comm Technology, " +
                            "School of Engineering and our niche School of Design & Media –" +
                            " offering a broad range of courses to meet diverse needs and interests",200,)*/
                }
            }
        });
        Button b_cca = findViewById(R.id.cca);
        b_cca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(institute)
                {
                    case "NP":
                        Intent i = new Intent(InstitutePage.this,CCAUI.class);
                        i.putExtra("institute", "NP");
                        startActivity(i);break;
                }
            }
        });
        Button b_intake = findViewById(R.id.intakerequirement);
        b_intake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InstitutePage.this,IntakeReqUI.class);
                startActivity(i);
            }
        });

    }
}