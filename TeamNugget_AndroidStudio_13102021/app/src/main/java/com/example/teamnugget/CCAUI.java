package com.example.teamnugget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CCAUI extends AppCompatActivity {
    RecyclerView recyclerView;
    CCAAdapter adapter;
    char instituteType;
    int instituteID;
    List<CCA> ccaList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cca_ui);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        recyclerView = findViewById(R.id.recycler_view);
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

        switch(instituteType)
        {
            case 'P':
               ccaList = csvParse.polytechnics.get(instituteID).getCCAs();break;
            case 'I':
                ccaList = csvParse.ites.get(instituteID).getCCAs();break;


        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CCAAdapter(this,ccaList);
        recyclerView.setAdapter(adapter);
    }


}