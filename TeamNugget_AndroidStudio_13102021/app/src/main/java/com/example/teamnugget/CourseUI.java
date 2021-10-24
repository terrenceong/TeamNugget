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
    char instituteType;
    int instituteID;
    int schoolID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        instituteType = getIntent().getCharExtra("instituteType", 'P');
        instituteID = getIntent().getIntExtra("instituteID",0);
        schoolID= getIntent().getIntExtra("schoolID",0);
        switch(instituteType) {
            case 'P':
                Log.d("info", csvParse.polytechnics.get(instituteID).getName());
                Log.d("info", csvParse.polytechnics.get(instituteID).getSchools().get(schoolID).getName());
                addCourseButtons(csvParse.polytechnics.get(instituteID).getSchools().get(schoolID).getCourses());
                break;
            case 'U':
                addCourseButtons(csvParse.universities.get(instituteID).getSchools().get(schoolID).getCourses());
                break;
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
            final int index = i;
            b_course.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),CourseInfo.class);
                    //courses.get(index).print(Character.toString(instituteType));
                    intent.putExtra("instituteType", instituteType);
                    intent.putExtra("instituteID", instituteID);
                    intent.putExtra("schoolID", schoolID);
                    intent.putExtra("courseID", index);
                    startActivity(intent);
                }
            });
            layout.addView(b_course);
        }
    }
}