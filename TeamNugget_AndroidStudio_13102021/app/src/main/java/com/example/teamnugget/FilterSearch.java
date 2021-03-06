package com.example.teamnugget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class FilterSearch extends AppCompatActivity {

    final float GPA_MAX = 4.0f;
    AutoCompleteTextView search;
    AutoCompleteTextView searchCourse;
    TextInputLayout ddpLayout;
    AutoCompleteTextView ddpFill;
    ArrayAdapter<String> arrayAdapter_schools;
    CourseAdapter arrayAdapter_courses;
    SeekBar gpaSlider;
    TextView gpaValue;
    ListView courseList;
    Switch switchButton;
    TextView gpaSwitch;
    TextView nameSwitch;
    static boolean clicked = false;

    Institute instituteFound;
    School schoolFound;
    Course courseFound;

    float gpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_search);

        //Initialise UI
        search = (AutoCompleteTextView)findViewById(R.id.searchInstitute);
        ddpLayout = (TextInputLayout)findViewById(R.id.ddmLayout);
        ddpFill = (AutoCompleteTextView)findViewById(R.id.ddmFill);
        gpaSlider = (SeekBar)findViewById(R.id.gpaSlider);
        gpaValue = (TextView)findViewById(R.id.gpaValue);
        courseList = (ListView)findViewById(R.id.courseList);
        switchButton = (Switch)findViewById(R.id.gpaSwitch);
        searchCourse = (AutoCompleteTextView)findViewById(R.id.searchCourse);
        gpaSwitch = (TextView)findViewById(R.id.gpaSW);
        nameSwitch = (TextView)findViewById(R.id.nameSW);

        //Search Name
        ArrayAdapter<String> arrayAdapter_search = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_layout, csvParse.instituteNames(csvParse.universities));
        search.setAdapter(arrayAdapter_search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (clicked)
                {
                    gpaSlider.setVisibility(View.INVISIBLE);
                    gpaValue.setVisibility(View.INVISIBLE);
                    courseList.setVisibility(View.INVISIBLE);
                    switchButton.setVisibility(View.INVISIBLE);
                    searchCourse.setVisibility(View.INVISIBLE);
                    nameSwitch.setVisibility(View.INVISIBLE);
                    gpaSwitch.setVisibility(View.INVISIBLE);

                    ddpFill.setText("School");
                    clicked = false;
                }
            }
        });
        //Selected Institute
        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ddpLayout.setVisibility(View.VISIBLE);


                instituteFound = (Institute) csvParse.Contains(csvParse.universities, arrayAdapter_search.getItem(position));
                arrayAdapter_schools = new ArrayAdapter<>(getApplicationContext(),R.layout.dropdown_layout, instituteFound.getSchoolName());
                ddpFill.setAdapter(arrayAdapter_schools);
                ddpFill.setThreshold(1);
                hideKeyboard(view);
                clicked = true;

            }
        });

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    gpaSlider.setVisibility(View.VISIBLE);
                    gpaValue.setVisibility(View.VISIBLE);

                    searchCourse.setVisibility(View.INVISIBLE);
                }
                else
                {
                    gpaSlider.setVisibility(View.INVISIBLE);
                    gpaValue.setVisibility(View.INVISIBLE);

                    searchCourse.setVisibility(View.VISIBLE);
                }
            }
        });

        //Selected School
        ddpFill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switchButton.setVisibility(View.VISIBLE);
                gpaSwitch.setVisibility(View.VISIBLE);
                nameSwitch.setVisibility(View.VISIBLE);
                if (switchButton.getText().equals("GPA"))
                {
                    gpaSlider.setVisibility(View.VISIBLE);
                    gpaValue.setVisibility(View.VISIBLE);

                    searchCourse.setVisibility(View.INVISIBLE);
                }
                else
                {
                    gpaSlider.setVisibility(View.INVISIBLE);
                    gpaValue.setVisibility(View.INVISIBLE);

                    searchCourse.setVisibility(View.VISIBLE);
                }
                schoolFound = (School) csvParse.Contains(instituteFound.getSchools(), arrayAdapter_schools.getItem(position));
                arrayAdapter_courses =  new CourseAdapter(getApplicationContext(), R.layout.dropdown_list, schoolFound.getCourses());
                courseList.setAdapter(arrayAdapter_courses);
                courseList.setVisibility(View.VISIBLE);
                hideKeyboard(view);



            }
        });

        gpaSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gpa = (progress/100.0f) * GPA_MAX;
                gpaValue.setText("" + gpa );
                arrayAdapter_courses =  new CourseAdapter(getApplicationContext(),R.layout.dropdown_list, schoolFound.similarCourses(gpa, false));
                courseList.setAdapter(arrayAdapter_courses);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        searchCourse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                arrayAdapter_courses =  new CourseAdapter(getApplicationContext(), R.layout.dropdown_list, schoolFound.similarCourses(searchCourse.getText().toString()));
                courseList.setAdapter(arrayAdapter_courses);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                courseFound = (Course) csvParse.Contains(schoolFound.getCourses(), arrayAdapter_courses.getItem(position).getName());
                Intent intent = new Intent(getApplicationContext(),CourseInfo.class);
                intent.putExtra("instituteType", csvParse.instituteDeterminator(instituteFound));
                intent.putExtra("instituteID", csvParse.originalIndex(instituteFound, csvParse.universities));
                intent.putExtra("schoolID", csvParse.originalIndex(schoolFound, instituteFound.getSchools()));
                intent.putExtra("courseID", csvParse.originalIndex(courseFound, schoolFound.getCourses()));
                startActivity(intent);
            }
        });





        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.filterSearch);
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
    public void hideKeyboard(View view)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
    public void closeKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null)
        {
            hideKeyboard(view);
        }
    }

}