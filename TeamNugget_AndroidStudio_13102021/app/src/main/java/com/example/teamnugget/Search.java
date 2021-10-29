package com.example.teamnugget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;

import java.util.*;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Search extends AppCompatActivity {
    SearchView sview;
    LinearLayout layout;
    TextView title;
    Button b_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        sview = findViewById(R.id.searchBar);
        layout = findViewById(R.id.resultsLayout);
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

        sview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                //Log.d("info", s);
                layout.removeAllViews();
                List<Institute> result = SearchSortAlgorithm.searchByCourses(csvParse.universities,s);
                Log.d("info", Integer.toString(result.size()));
                for(int i = 0;i<result.size();i++) {
                    title = new TextView(getApplicationContext());
                    title.setText(result.get(i).getName());
                    layout.addView(title);
                    for (int j = 0; j < result.get(i).getSchools().size(); j++) {
                        for (int k = 0; k < result.get(i).getSchools().get(j).getCourses().size(); k++) {
                            b_result = new Button(getApplicationContext());
                            b_result.setText(result.get(i).getSchools().get(j).getCourses().get(k).getName());
                            b_result.setBackgroundResource(R.drawable.button);
                            layout.addView(b_result);
                            //result.get(i).getSchools().get(j).getCourses().get(k).print("U");
                            //Log.d("info", result.get(i).getSchools().get(j).getCourses().get(k).getName());
                        }
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //Log.d("info", s);

                return false;
            }
        });


    }
}