package com.example.teamnugget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SchoolUI extends AppCompatActivity {
    Button b_school;
    String institute;
    char instituteType;
    int instituteIndex;  //Index for a particular institute within the institute's list
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        institute = getIntent().getStringExtra("institute");
        /*
        ITE List Index:

        Polytechnic List Index:
        0 = NP
        1 = NYP
        2 = RP
        3 = SP
        4 = TP

        Uni List Index:
        */
        switch(institute)
        {
            case "ITECC":
                instituteType = 'I';
                break;
            case "ITEEAST":
                instituteType = 'I';
                break;
            case "ITEWEST":
                instituteType = 'I';
                break;
            case "NP":
                instituteType = 'P';
                instituteIndex = 0;
                addSchoolButtons(csvParse.polytechnics.get(0).getSchools());
                break;
            case "NYP":
                instituteType = 'P';
                instituteIndex = 1;
                addSchoolButtons(csvParse.polytechnics.get(1).getSchools());
                break;
            case "RP":
                instituteType = 'P';
                instituteIndex = 2;
                addSchoolButtons(csvParse.polytechnics.get(2).getSchools());
                break;
            case "SP":
                instituteType = 'P';
                instituteIndex = 3;
                addSchoolButtons(csvParse.polytechnics.get(3).getSchools());
                break;
            case "TP":
                instituteType = 'P';
                instituteIndex = 4;
                addSchoolButtons(csvParse.polytechnics.get(4).getSchools());
                break;
            default:
                break;

        }
        /*for(int i = 0; i<5; i++){
            addSchoolButton("Test "+i);
        }*/
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


    }
    public void addSchoolButtons(List<School> school){
        LinearLayout layout = (LinearLayout)findViewById(R.id.schoolLayout) ;
        for(int i = 0 ; i < school.size(); i++) {
            b_school = new Button(this);
            final int schoolIndex = i; //schoolIndex is the index of a school within getSchools()
            b_school.setText(school.get(i).getName());
            b_school.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),CourseUI.class);
                    intent.putExtra("instituteType", instituteType);
                    intent.putExtra("instituteIndex", instituteIndex);
                    intent.putExtra("schoolIndex", schoolIndex);
                    startActivity(intent);
                }
            });
            layout.addView(b_school);
        }
    }
}