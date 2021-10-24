package com.example.teamnugget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Locale;

public class JCHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Institute> jcList = csvParse.juniorcolleges;
        setContentView(R.layout.activity_jc_home);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Button btn_JAE = findViewById(R.id.btnJAE);
        Button btn_DSA = findViewById(R.id.btnDSA);
        Button btn_SL = findViewById(R.id.btnStudentLife);
        Button btn_S = findViewById(R.id.btnScholarships);
        TextView header = findViewById(R.id.JCName);
        int jcPosition = getIntent().getIntExtra("jcPosition",0);

        header.setText(jcList.get(jcPosition).getName());

        btn_JAE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(JCHome.this,JCJointAdmission.class);
                i.putExtra("jcPosition", jcPosition);
                startActivity(i);
            }
        });
        btn_DSA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(JCHome.this,JCDirectAdmission.class);
                i.putExtra("jcPosition", jcPosition);
                startActivity(i);
            }
        });
        btn_SL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(JCHome.this,PostSecondaryUI.class);
                i.putExtra("jcPosition", jcPosition);
                startActivity(i);
            }
        });
        btn_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(JCHome.this,PostSecondaryUI.class);
                i.putExtra("jcPosition", jcPosition);
                startActivity(i);
            }
        });

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
        csvParse.printInstitutes();
    }
}
