package com.example.teamnugget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class JCHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Institute> jcList = csvParse.juniorcolleges;
        setContentView(R.layout.activity_jc_home);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Button btn_JAE = findViewById(R.id.btnJAE);
        Button btn_DSA = findViewById(R.id.btnDSA);
        Button btn_CCA = findViewById(R.id.btnCCA);
        TextView header = findViewById(R.id.JCName);
        int jcPosition = getIntent().getIntExtra("jcPosition",0);
        ImageView img = (ImageView) findViewById(R.id.imageView);

        header.setText(jcList.get(jcPosition).getName());

        btn_JAE.setBackgroundResource(R.drawable.button);
        btn_JAE.setBackground(getResources().getDrawable(R.drawable.button));
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
        btn_CCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CCAUI.class);//can add if statement depending on institute type
                i.putExtra("institute", 'J');
                i.putExtra("instituteID", jcPosition);
                startActivity(i);
                startActivity(i);
            }
        });

        switch(jcPosition) {
            case 0:
                img.setBackgroundResource(R.drawable.asrjc);
                break;
            case 1:
                img.setBackgroundResource(R.drawable.acjc);
                break;
            case 2:
                img.setBackgroundResource(R.drawable.acsi);
                break;
            case 3:
                img.setBackgroundResource(R.drawable.cjc);
                break;
            case 4:
                img.setBackgroundResource(R.drawable.dhs);
                break;
            case 5:
                img.setBackgroundResource(R.drawable.ejc);
                break;
            case 6:
                img.setBackgroundResource(R.drawable.hwaci);
                break;
            case 7:
                img.setBackgroundResource(R.drawable.jpjc);
                break;
            case 8:
                img.setBackgroundResource(R.drawable.mi);
                break;
            case 9:
                img.setBackgroundResource(R.drawable.nyjc);
                break;
            case 10:
                img.setBackgroundResource(R.drawable.njc);
                break;
            case 11:
                img.setBackgroundResource(R.drawable.ri);
                break;
            case 12:
                img.setBackgroundResource(R.drawable.rvhs);
                break;
            case 13:
                img.setBackgroundResource(R.drawable.sajc);
                break;
            case 14:
                img.setBackgroundResource(R.drawable.sji);
                break;
            case 15:
                img.setBackgroundResource(R.drawable.tmjc);
                break;
            case 16:
                img.setBackgroundResource(R.drawable.tjc);
                break;
            case 17:
                img.setBackgroundResource(R.drawable.vjc);
                break;
            case 18:
                img.setBackgroundResource(R.drawable.yijc);
                break;
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
        csvParse.printInstitutes();
    }
}
