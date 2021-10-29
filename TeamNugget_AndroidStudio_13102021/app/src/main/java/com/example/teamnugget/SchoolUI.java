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
    char instituteType;
    int instituteID;  //Index for a particular institute within the institute's list
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        instituteType = getIntent().getCharExtra("institute", 'P');
        instituteID = getIntent().getIntExtra("instituteID", 0);
        /*
        ITE List Index:
        0 = East
        1 = Central
        2 = West

        Polytechnic List Index:
        0 = NP
        1 = NYP
        2 = RP
        3 = SP
        4 = TP

        Uni List Index:
        0 = NTU
        1 = NUS
        2 = SMU
        3 = SIT
        4 = SUTD
        5 = SUSS
        */
        switch(instituteType)
        {
            case 'I':
                addSchoolButtons(csvParse.ites.get(instituteID).getSchools());
                break;
            case 'P':
                addSchoolButtons(csvParse.polytechnics.get(instituteID).getSchools());
                break;
            case 'U':
                addSchoolButtons(csvParse.universities.get(instituteID).getSchools());

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
            final int schoolID = i; //schoolIndex is the index of a school within getSchools()
            b_school.setText(school.get(i).getName());
            b_school.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),CourseUI.class);
                    intent.putExtra("instituteType", instituteType);
                    intent.putExtra("instituteID", instituteID);
                    intent.putExtra("schoolID", schoolID);
                    startActivity(intent);
                }
            });
            //b_school.setBackground(R.id.draw);
        }
            layout.addView(b_school);
    }
}