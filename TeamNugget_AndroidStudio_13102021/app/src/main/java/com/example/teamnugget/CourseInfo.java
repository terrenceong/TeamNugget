package com.example.teamnugget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CourseInfo extends AppCompatActivity {
    int instituteType;
    TextView courseTitle;
    TextView desc;
    TextView fullTime;
    TextView instituteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("MyApp","I am here");
        setContentView(R.layout.activity_course_info);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        //institute is the parameter to determine what information to display here
        String institute = getIntent().getStringExtra("institute");
        //char instituteType = getIntent().getCharExtra("institiuteType", 'P');
        instituteType = getIntent().getCharExtra("instituteType", 'P');
        int instituteID = getIntent().getIntExtra("instituteID",0);
        int schoolID= getIntent().getIntExtra("schoolID",0);
        int courseID = getIntent().getIntExtra("courseID", 0);
        courseTitle = (TextView) findViewById(R.id.courseTitle);
        desc = (TextView) findViewById(R.id.description);
        fullTime = (TextView) findViewById(R.id.fullTime);
        instituteText = (TextView) findViewById(R.id.instituteName);
        cutoff = (TextView) findViewById(R.id.cutOffPoints);
        switch(instituteType){
            case 'P':
                instituteText.setText(csvParse.polytechnics.get(instituteID).getName());
                setText(csvParse.polytechnics.get(instituteID).getSchools().get(schoolID).getCourses().get(courseID));
                break;
            case 'U':
                instituteText.setText(csvParse.universities.get(instituteID).getName());
                setText(csvParse.universities.get(instituteID).getSchools().get(schoolID).getCourses().get(courseID));
                break;
            case 'I':
                instituteText.setText(csvParse.ites.get(instituteID).getName());
                setText(csvParse.ites.get(instituteID).getSchools().get(schoolID).getCourses().get(courseID));
                break;
        }
        //header.setText(institute); // set text.
        // alternatively if u have image do this
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


    }

    public void setText(Course course){
        courseTitle.setText(course.getName());
        desc.setText(course.getDescription());
        fullTime.setText("Type: "+course.isFullTime());
        if(instituteType == 'U'){
            UniversityCourse uc = (UniversityCourse)course;
            if(uc.getCutOffPointsGPA() < 0 || uc.getCutOffPointsAL() == "#")
                cutoff.setText("GPA Req: N/A\nA-Level Req: N/A");
            else
                cutoff.setText("GPA Req: "+uc.getCutOffPointsGPA()+"\nA-Level Req:"+uc.getCutOffPointsAL());
        }
        else
            cutoff.setText(" ");
    }
}