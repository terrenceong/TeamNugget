package com.example.teamnugget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class IntakeReqUI extends AppCompatActivity {
    RecyclerView intakereq;
    TextView title1,title2,title3;
    char instituteType;
    int instituteID;
    List<School> schoolList;
    List<String> alevel = new ArrayList<>();
    List<String> gpa = new ArrayList<>();
    List<String> name = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intake_req_ui);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
         intakereq = findViewById(R.id.recycler_viewIntake);
         title1 = findViewById(R.id.title1);
         title2 = findViewById(R.id.title2);
         title3 = findViewById(R.id.title3);
        instituteType = getIntent().getCharExtra("institute", 'P');
        instituteID = getIntent().getIntExtra("instituteID", 0);


        setRecyclerView();


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
    private void setRecyclerView()
    {

       //String str = schoolList.get(0).getName();
        switch (instituteType)
        {
            case 'U':schoolList = csvParse.universities.get(instituteID).getSchools();
                title1.setText("Programme");
                title2.setText("A-level");
                title3.setText("GPA");
                for(School s:schoolList)
                {
                    for(Course c:s.getCourses())
                    {
                        name.add(c.getName());
                        if(c.getCutOffPointsGPA()==-1)
                            gpa.add("NA");
                        else
                            gpa.add(c.getCutOffPointsGPA()+"");
                        alevel.add(c.getCutOffPointsAL());
                    }
                }
                break;
            case 'P':schoolList = csvParse.polytechnics.get(instituteID).getSchools();
                title1.setText("Programme");
                title3.setText("Cut-off points");
                title2.setVisibility(View.INVISIBLE);
                for(School s:schoolList)
                {
                    for(Course c:s.getCourses())
                    {
                        name.add(c.getName());
                       alevel.add("");
                        gpa.add(c.getCutOffPoints()+"");
                    }
                }
                break;
            case 'I':schoolList = csvParse.ites.get(instituteID).getSchools();
                title1.setText("Programme");
                title3.setText("O Level");
                title2.setText("N Level");
                for(School s:schoolList)
                {
                    for(Course c:s.getCourses())
                    {
                        name.add(c.getName());
                        alevel.add("NA");
                        gpa.add("NA");
                    }
                }
                break;

        }


        /*List<UniversityCourse> universityCourses = new ArrayList<>();
        universityCourses.add(new UniversityCourse("Accountancy","true","Nanyang Business School (College of Business) ","BBC/C",3.74,"Nanyang Business School (College of Business) "));
        universityCourses.add(new UniversityCourse("Aerospace Engineering ","true","College of Engineering ","AAB/C",3.8,"College of Engineering "));*/
        intakereq.setHasFixedSize(true);
        intakereq.setLayoutManager(new LinearLayoutManager(this));
        IntakeReqAdapater adapter = new IntakeReqAdapater(this,alevel,gpa,name);
        intakereq.setAdapter(adapter);

    }
}