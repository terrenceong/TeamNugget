package com.example.teamnugget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Button diploma = findViewById(R.id.postdiploma);
        Button b_postSec = findViewById(R.id.postSecondary);
        diploma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,PostALevelDiplomaUI.class);
                startActivity(i);
            }
        });
        b_postSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,PostSecondaryUI.class);
                startActivity(i);
            }
        });
        //set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

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
        parseCSV();
        csvParse.printInstitutes();
    }
    public void parseCSV()
    {
        csvParse cp = new csvParse();
        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){

            int resourceID = 0;
            try
            {
                resourceID = fields[count].getInt(fields[count]);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            System.out.println("Raw Asset: " + fields[count].getName() + "ID: " + resourceID);
            InputStream is = getResources().openRawResource(resourceID);
            cp.parseData(is, fields[count].getName() );
        }
    }
}