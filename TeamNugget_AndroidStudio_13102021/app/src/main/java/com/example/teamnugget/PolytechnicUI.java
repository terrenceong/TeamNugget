package com.example.teamnugget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class PolytechnicUI extends AppCompatActivity {
    Button b_poly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polytechnic_list);
        addPolyButtons(csvParse.polytechnics);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
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
    public void addPolyButtons(List<Institute> poly){
        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout) ;
        for(int i = 0 ; i < poly.size(); i++) {
            b_poly = new Button(this);
            b_poly.setText(poly.get(i).getName());
            final int id = i;
            b_poly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),InstitutePage.class);
                    i.putExtra("institute", 'P');
                    i.putExtra("instituteID", id);
                    startActivity(i);
                }
            });
            layout.addView(b_poly);
        }
    }
}