package com.example.teamnugget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class JCJointAdmission extends AppCompatActivity {
    int jcPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Institute> jcList = csvParse.juniorcolleges;
        setContentView(R.layout.activity_jc_joint);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        jcPosition = getIntent().getIntExtra("jcPosition",0);

        TextView header = findViewById(R.id.JCNameJoint);
        TextView artsView = findViewById(R.id.PointArts);
        TextView scienceView = findViewById(R.id.PointScience);
        header.setText(jcList.get(jcPosition).getName());
        artsView.setText(Integer.toString(((JuniorCollege)jcList.get(jcPosition)).getPointsArts()));
        scienceView.setText(Integer.toString(((JuniorCollege)jcList.get(jcPosition)).getPointsScience()));

        //perform item selectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.filterSearch:
                        startActivity(new Intent(getApplicationContext(), FilterSearch.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }
}