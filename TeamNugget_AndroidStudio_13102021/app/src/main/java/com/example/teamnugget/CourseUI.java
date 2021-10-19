package com.example.teamnugget;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class CourseUI extends AppCompatActivity {
    Button b_course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        char instituteType = getIntent().getCharExtra("instituteType", 'P');
        int instituteIndex = getIntent().getIntExtra("instituteIndex",0);
        int schoolIndex = getIntent().getIntExtra("schoolIndex",0);
        switch(instituteType) {
            case 'P':
                Log.d("info", csvParse.polytechnics.get(instituteIndex).getName());
                Log.d("info", csvParse.polytechnics.get(instituteIndex).getSchools().get(schoolIndex).getName());
                addCourseButtons(csvParse.polytechnics.get(instituteIndex).getSchools().get(schoolIndex).getCourses());
            default:
                break;
        }
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
       /* switch(institute)
        {
            case "ITECC":

                break;
            case "ITEEAST":

                break;
            case "ITEWEST":

                break;
            case "NP":
                addSchoolButtons(csvParse.polytechnics.get(0).getSchools());
                break;
            case "NYP":
                addSchoolButtons(csvParse.polytechnics.get(1).getSchools());
                break;
            case "RP":
                addSchoolButtons(csvParse.polytechnics.get(2).getSchools());
                break;
            case "SP":
                addSchoolButtons(csvParse.polytechnics.get(3).getSchools());
                break;
            case "TP":
                addSchoolButtons(csvParse.polytechnics.get(4).getSchools());
                break;
            default:
                break;

        }*/
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
    public void addCourseButtons(List<Course> courses){
        LinearLayout layout = (LinearLayout)findViewById(R.id.courseLayout) ;
        for(int i = 0 ; i < courses.size(); i++) {
            b_course = new Button(this);
            b_course.setText(courses.get(i).getName());
            layout.addView(b_course);
        }
    }
}