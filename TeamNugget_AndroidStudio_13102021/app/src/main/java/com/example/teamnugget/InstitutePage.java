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
        char institute = getIntent().getCharExtra("institute", 'P');
        //char instituteType = getIntent().getCharExtra("institiuteType", 'P');
        int instituteID = getIntent().getIntExtra("instituteID", 0);

        TextView header = (TextView) findViewById(R.id.textView);
        //header.setText(institute); // set text.
        // alternatively if u have image do this
        switch(institute){
            case 'I':
                switch(instituteID) {
                    case 1:
                        header.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.sg_itec, 0, 0, 0);
                        break;
                    case 0:
                        header.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.sg_itee, 0, 0, 0);
                        break;
                    case 2:
                        header.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.sg_itew, 0, 0, 0);
                        break;
                }break;
            case 'P':
                switch(instituteID) {
                    case 0:
                        header.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.np, 0, 0, 0);
                        break;
                    case 1:
                        header.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.nyp, 0, 0, 0);
                        break;
                    case 2:
                        header.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.rp, 0, 0, 0);
                        break;
                    case 3:
                        header.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.sp, 0, 0, 0);
                        break;
                    case 4:
                        header.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.tp, 0, 0, 0);
                        break;
                }break;
            case 'U':
                switch(instituteID) {
                    case 0:
                        header.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.ntu, 0, 0, 0);
                        break;
                    case 1:
                        header.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.nus, 0, 0, 0);
                        break;
                    case 2:
                        header.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.smu, 0, 0, 0);
                        break;
                    case 3:
                        header.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.sit, 0, 0, 0);
                        break;
                    case 4:
                        header.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.sutd, 0, 0, 0);
                        break;
                    case 5:
                        header.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.suss, 0, 0, 0);
                        break;
                }break;
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
                i.putExtra("instituteID", instituteID);
                startActivity(i);
            }
        });
        Button b_cca = findViewById(R.id.cca);
        b_cca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(institute)
                {
                    case 'P':
                        switch(instituteID)
                        {
                            case 1:
                                Intent i = new Intent(InstitutePage.this,CCAUI.class);
                                i.putExtra("institute", "NP");
                                startActivity(i);break;
                        }
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