package com.example.teamnugget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ITEUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iteui);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        Button b_itecc = findViewById(R.id.itecc);
        Button b_iteeast = findViewById(R.id.iteeast);
        Button b_itewest = findViewById(R.id.itewest);
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
        b_itecc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),InstitutePage.class);
                i.putExtra("institute", "ITECC");
                startActivity(i);
            }
        });

        b_iteeast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),InstitutePage.class);
                i.putExtra("institute", "ITEEAST");
                startActivity(i);
            }
        });
        b_itewest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),InstitutePage.class);
                i.putExtra("institute", "ITEWEST");
                startActivity(i);
            }
        });
    }
}